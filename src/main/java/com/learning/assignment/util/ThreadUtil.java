package com.learning.assignment.util;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadUtil {

    public static void waitForAllThreadsToComplete(List<Thread> threads, AtomicInteger errorCount) {
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                errorCount.getAndIncrement();
            }
        }
    }

    public static void sleep(long interval, AtomicInteger errorCount) {
        try {
            // Wait for some time to demonstrate threads
            Thread.sleep(interval);
        } catch (InterruptedException e) {
            e.printStackTrace();
            errorCount.getAndIncrement();
        }
    }
}
