package com.learning.assignment.test;

import com.learning.assignment.Consumer;
import com.learning.assignment.MessageQueue;
import com.learning.assignment.Producer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.learning.assignment.util.ThreadUtil.sleep;
import static com.learning.assignment.util.ThreadUtil.waitForAllThreadsToComplete;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProducerConsumerTest {

    private MessageQueue messageQueue;
    private AtomicInteger producedCount;
    private AtomicInteger consumedCount;
    private AtomicInteger errorCount;


    @BeforeEach
    void setUp() {
        messageQueue = new MessageQueue(10);
        producedCount = new AtomicInteger(0);
        consumedCount = new AtomicInteger(0);
        errorCount = new AtomicInteger(0);
    }

    @Test
    void testSuccessfulProcessing() throws InterruptedException {
        List<Producer> producers = new ArrayList<>();
        List<Consumer> consumers = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Producer producer = new Producer(messageQueue, producedCount, errorCount, 5);
            Thread producerThread = new Thread(producer);
            producerThread.start();
            threads.add(producerThread);
            producers.add(producer);
        }
        for (int i = 0; i < 5; i++) {
            Consumer consumer = new Consumer(messageQueue, consumedCount, errorCount, 5);
            Thread consumerThread = new Thread(consumer);
            consumerThread.start();
            threads.add(consumerThread);
            consumers.add(consumer);
        }
        // let threads run for some time
        sleep(100, errorCount);
        // stop threads
        consumers.forEach(Consumer::stop);
        producers.forEach(Producer::stop);
        waitForAllThreadsToComplete(threads, errorCount);

        assertEquals(5, producedCount.get());
        assertEquals(5, consumedCount.get());
        assertEquals(0, errorCount.get());
    }

    @Test
    void testFailureScenario() {
        // Here, we intentionally interrupt the producer thread to simulate a failure
        Producer producer = new Producer(messageQueue, producedCount, errorCount, 1);
        Thread producerThread = new Thread(producer);
        producerThread.start();
        producerThread.interrupt();

        try {
            producerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        assertTrue(errorCount.get() > 0);
    }
}
