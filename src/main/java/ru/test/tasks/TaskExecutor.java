package ru.test.tasks;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;


public class TaskExecutor {

    public static void main(String[] args) {

        ExecutorService pool = Executors.newFixedThreadPool(3);
        pool.submit(() -> asyncExecuteExclusiveTask());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pool.submit(() -> asyncExecuteConcurrentTask(1));
        pool.submit(() -> asyncExecuteConcurrentTask(2));
        pool.submit(() -> asyncExecuteConcurrentTask(3));
        pool.submit(() -> asyncExecuteExclusiveTask());
        pool.submit(() -> asyncExecuteConcurrentTask(4));
        pool.submit(() -> asyncExecuteConcurrentTask(5));

    }


    /**
     * Потокобезопасное хранилище данных
     */
    private static Collection<Object> dataStore = new ConcurrentHashSet<Object>();

    private static final Semaphore sem = new Semaphore(1);
    private static final Semaphore sem2 = new Semaphore(5);

    private static final String OK = "OK";
    private static final String FAIL = "FAIL";

    /**
     * Выполнение задания эксклюзивного типа Добавление данных в хранилище,
     * если хотя бы один элемент уже существует, данные не добавляются
     *
     * @param newData новые данные
     * @return если хотя бы один элемент уже существует, возвращается результат с
     * ошибкой, иначе возвращается успешный результат
     */
    private static String executeExclusiveTask(Collection<Object> newData) {
        for (Object element : newData) {
            if (dataStore.contains(element)) {
                return FAIL;
            }
        }
        dataStore.addAll(newData);
        return OK;
    }

    /**
     * Выполнение задания параллельного типа.
     * Добавление данных в хранилище,
     * если элемент существует, в результат помещается информация,
     * о том, что добавление элемента не произошло,
     * в другом случает в результат помещается информация о успешном добавлении
     *
     * @param newData новые данные
     * @return данные по результатам добавления каждого их новых элементов
     */
    private static Map<Object, String> executeConcurrentTask(Collection<Object> newData) {


        Map<Object, String> result = new HashMap<Object, String>();
        for (Object element : newData) {
            if (dataStore.contains(element)) {
                result.put(element, FAIL);
            } else {
                dataStore.add(element);
                result.put(element, OK);
            }
        }
        return result;
    }

    /**
     * Выполнение задания первого типа в отдельном потоке
     */
    public static void asyncExecuteExclusiveTask() {
        //

        System.out.println("Thread exclusive start");

        if (sem2.availablePermits() < 5) return;

        try {
            System.out.println("Thread exclusive ожидает разрешения");

            System.out.println("Thread exclusive available permits = " + sem.availablePermits());

            sem.acquire();
            System.out.println("Thread exclusive получает разрешение");

        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }

        List<Object> stringList = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            stringList.add("String" + TaskExecutor.getRandomNumberFrom(0, 100));
        }

        TaskExecutor.executeExclusiveTask(stringList);

        for (Object item : dataStore.toArray()) {
            System.out.println("Thread exclusive dataStore contain = " + item);
        }

        System.out.println("Thread exclusive получает разрешение");
        sem.release();

        System.out.println("Thread exclusive end");


    }

    /**
     * Выполнение задания второго типа в отдельном потоке
     */
    public static void asyncExecuteConcurrentTask(Integer threadNum) {
        boolean permit = false;

        System.out.println("Thread " + threadNum + " concurent ожидает разрешения");

        System.out.println("Thread " + threadNum + " concurent available permits = " + sem.availablePermits());
        if (sem.availablePermits() == 0) return;

        try {
            permit = sem2.tryAcquire(1, TimeUnit.SECONDS);

            if (permit) {

                System.out.println("Thread " + threadNum + " concurent получает разрешение");


                List<Object> stringList = new ArrayList<>();

                for (int i = 0; i < 100; i++) {
                    stringList.add("String" + TaskExecutor.getRandomNumberFrom(0, 100));
                }

                Map<Object, String> result = TaskExecutor.executeConcurrentTask(stringList);

                for (Map.Entry entry : result.entrySet()) {
                    System.out.println("Thread " + threadNum + " dataStore result = " + entry.toString());
                }

            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (permit) {
                sem2.release();
            }
        }

        System.out.println("Thread " + threadNum + " concurent освобождает разрешение");


    }


    public static int getRandomNumberFrom(int min, int max) {
        Random foo = new Random();
        int randomNumber = foo.nextInt((max + 1) - min) + min;

        return randomNumber;
    }
}
