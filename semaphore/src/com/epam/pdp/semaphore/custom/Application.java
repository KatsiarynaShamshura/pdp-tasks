package com.epam.pdp.semaphore.custom;

public class Application {

    static int count;

    public static void main(String[] args) {
        MySemaphore mySemaphore = new MySemaphore(3);

        MyThread ts1 = new MyThread(mySemaphore);
        MyThread ts2 = new MyThread(mySemaphore);
        MyThread ts3 = new MyThread(mySemaphore);

        new Thread(ts1, "D").start();
        new Thread(ts2, "E").start();
        new Thread(ts3, "F").start();
    }
}
