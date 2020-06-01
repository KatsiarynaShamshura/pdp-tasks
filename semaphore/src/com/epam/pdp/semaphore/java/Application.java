package com.epam.pdp.semaphore.java;

import java.util.concurrent.Semaphore;

public class Application {

    static int count;

    public static void main(String[] args) {

        Semaphore sem = new Semaphore(2);

        MyThread mt1 = new MyThread(sem);
        MyThread mt2 = new MyThread(sem);
        MyThread mt3 = new MyThread(sem);

        new Thread(mt1, "A").start();
        new Thread(mt2, "B").start();
        new Thread(mt3, "C").start();
    }
}
