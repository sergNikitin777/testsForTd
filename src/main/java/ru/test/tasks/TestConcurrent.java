package ru.test.tasks;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TestConcurrent {
    static Random r = new Random();
    public static String getRandomMac() {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < 6; i++) {
            int rand = r.nextInt(256);
            if (buf.length() > 0)
                buf.append(":");
            String s = String.format("%02x", rand);
            buf.append(s);
        }

        return buf.toString();
    }

    //static ConcurrentMap<String, Integer> macs = new ConcurrentHashMap<>();
    static ConcurrentMap<String, Integer> macs = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        for (int i = 0; i < 1000000; i++) {
            macs.put(getRandomMac(), r.nextInt(10000));
        }
        for (int i = 0; i < 50; i++) {
            Thread t1 = new Thread(new Thread1(), "t1-" + i);
            t1.start();
        }
        for (int i = 0; i < 50; i++) {
            Thread t2 = new Thread(new Thread2(), "t2-" + i);
            t2.start();
        }
        for (int i = 0; i < 50; i++) {
            Thread t3 = new Thread(new Thread3(), "t3-" + i);
            t3.start();
        }
    }

    static class Thread1 implements Runnable {
        @Override
        public void run() {
            Set<String> keys = macs.keySet();

            for (String mac: keys) {
                int randomIndex = r.nextInt(100);
                if (randomIndex % 3 == 0)
                    macs.remove(mac);
            }
            System.out.println(Thread.currentThread().getName() + " done");
            System.out.println("Map size " + macs.size());
        }
    }

    static class Thread2 implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            for (int i = 0; i < 100; i++) {
                int randomIndex = r.nextInt(100);
                if (randomIndex % 2 == 0)
                    macs.put(getRandomMac(), new Integer(i));
            }
            System.out.println(Thread.currentThread().getName() + " done");
            System.out.println("Map size " + macs.size());
        }
    }

    static class Thread3 implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Set<String> keys = macs.keySet();
            for (String mac: keys) {
                int randomIndex = r.nextInt(100);
                if (randomIndex % 2 == 0)
                    macs.remove(mac);
            }
            System.out.println(Thread.currentThread().getName() + " done");
            System.out.println("Map size " + macs.size());
        }
    }
}
