package ru.test.tasks;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public class TestConcurrent2 {


    private final static Map<String, MyObject> state = new ConcurrentHashMap<String, MyObject>();
    private final static StateContainer stateContainer = new StateContainer(state);

    public static void main(String[] args) {
//        for (int i = 0; i < 1000000; i++) {
//            macs.put(getRandomMac(), r.nextInt(10000));
//        }

            Thread t1 = new Thread(new Thread1(), "t1-" + 1);
            t1.start();

            Thread t2 = new Thread(new Thread2(), "t2-" + 2);
            t2.start();

            Thread t3 = new Thread(new Thread3(), "t3-" + 3);
            t3.start();

    }

    static class Thread1 implements Runnable {
        @Override
        public void run() {

            for (int i = 0; i < 100; i++) {
                stateContainer.updateObject("" + i, new MyObject("1" + i));
                System.out.println("Thread 1 put i=" + i + " 1" + i);
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Thread2 implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                stateContainer.updateObject("" + i, new MyObject("1" + i));
                System.out.println("Thread 1 put i=" + i + " 1" + i);
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Thread3 implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                stateContainer.updateObject("" + i, new MyObject("1" + i));
                System.out.println("Thread 1 put i=" + i + " 1" + i);
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
