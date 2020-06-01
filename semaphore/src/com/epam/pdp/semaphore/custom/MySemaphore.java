package com.epam.pdp.semaphore.custom;

public class MySemaphore {

    private int countThread;

    public MySemaphore(int countThread) {
        this.countThread = countThread;
    }

    public synchronized void acquire() throws InterruptedException {
        if (countThread == 0) {
            this.wait();
        }
        countThread--;
    }

    public synchronized void release() {
        countThread++;
        this.notify();
    }
}
