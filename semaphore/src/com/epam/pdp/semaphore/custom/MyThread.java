package com.epam.pdp.semaphore.custom;


public class MyThread implements Runnable {

    private final MySemaphore mySemaphore;

    public MyThread(MySemaphore mySemaphore) {
        this.mySemaphore = mySemaphore;
    }

    @Override
    public void run() {
        System.out.println("Starting " + Thread.currentThread().getName());
        try {
            System.out.println(Thread.currentThread().getName() + " is waiting for a permit.");
            mySemaphore.acquire();
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
        mySemaphore.release();
    }
}
