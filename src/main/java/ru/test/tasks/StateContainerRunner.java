package ru.test.tasks;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class StateContainerRunner {
    //private  final static Map<String,MyObject> state = Collections.synchronizedMap(new HashMap());
    private final static Map<String, MyObject> state = new ConcurrentHashMap<String, MyObject>();
    private final static StateContainer stateContainer = new StateContainer(state);

    public StateContainerRunner() {
        //state =   Collections.synchronizedMap(new HashMap());
        //state =   Collections.synchronizedMap(new HashMap());
    }


    public static void main(String[] args) {

        System.out.println("Creating Executor Service with a thread pool of Size 3");
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        Runnable task1 = () -> {
            System.out.println("Executing Task1 inside : " + Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(2);
                for (int i = 0; i < 1000000; i++) {
                    stateContainer.updateObject("" + i, new MyObject("1" + i));
                    System.out.println("Thread 1 put i=" + i + " 1" + i);
                }
            } catch (InterruptedException ex) {
                throw new IllegalStateException(ex);
            }
        };

        Runnable task2 = () -> {
            System.out.println("Executing Task2 inside : " + Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(2);
                for (int i = 0; i < 1000000; i++) {
                    stateContainer.updateObject("" + i, new MyObject("2" + i));
                    System.out.println("Thread 2 put i=" + i + " 1" + i);
                }
            } catch (InterruptedException ex) {
                throw new IllegalStateException(ex);
            }
        };

        Runnable task3 = () -> {
            System.out.println("Executing Task3 inside : " + Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(2);
                for (int i = 0; i < 1000000; i++) {
                    String res = stateContainer.getObject("" + i).string;
                    System.out.println("Thread 3 get string=" + res);
                }
            } catch (InterruptedException ex) {
                throw new IllegalStateException(ex);
            }
        };


        System.out.println("Submitting the tasks for execution...");
        executorService.submit(task1);
        executorService.submit(task2);
        executorService.submit(task3);

        executorService.shutdown();

    }
}
