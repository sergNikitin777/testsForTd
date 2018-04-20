package ru.test.tasks;

import java.util.Date;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class MultiThreadTest {
    static Object object = new Object();

    private final static Map<String, MyObject> state = new ConcurrentHashMap<String, MyObject>();
    private final static StateContainer stateContainer = new StateContainer(state);

    public static void main(String[] args) {
        new Thread(){
            @Override
            public void run() {
                while(true){
//                    try {
//                        //Thread.sleep(1);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    Date date = new Date();
                    stateContainer.updateObject(""+1,new MyObject(date.toString()));
                    System.out.println("I am coming from first thread i.e. " + Thread.currentThread().getId() + " : " + date);
                }
            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                while(true){
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    Date date = new Date();
                    stateContainer.updateObject(""+1,new MyObject(date.toString()));
                    System.out.println("I am coming from second thread i.e. " + Thread.currentThread().getId() + " : " + date);
                }
            }
        }.start();
    }
}
