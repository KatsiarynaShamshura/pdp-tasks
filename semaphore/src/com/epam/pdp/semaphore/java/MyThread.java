package com.epam.pdp.semaphore.java;

import java.util.concurrent.Semaphore;

public class MyThread implements Runnable {

    private final Semaphore semaphore;

    public MyThread(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        System.out.println("Starting " + Thread.currentThread().getName());
        try {
            System.out.println(Thread.currentThread().getName() + " is waiting for a permit.");
            semaphore.acquire();
            System.out.println(Thread.currentThread().getName() + " gets a permit.");
            for (int i = 0; i < 5; i++) {
                Application.count++;
                System.out.println(Thread.currentThread().getName() + ": " + Application.count);
                Thread.sleep(10);
            }
        } catch (InterruptedException exc) {
            System.out.println(exc);
        }
        System.out.println(Thread.currentThread().getName() + " releases the permit.");
        semaphore.release();
    }
}
