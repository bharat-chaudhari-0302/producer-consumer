package com.learning.assignment;

import com.learning.assignment.util.ThreadUtil;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class Consumer implements Runnable {
    private static final Logger log = Logger.getLogger(Consumer.class.getCanonicalName());
    private final MessageQueue messageQueue;
    private final AtomicInteger consumedCount;
    private final AtomicInteger errorCount;
    private final int messageCount;
    private boolean running = false;

    public Consumer(MessageQueue messageQueue, AtomicInteger consumedCount, AtomicInteger errorCount, int messageCount) {

        this.messageQueue = messageQueue;
        this.errorCount = errorCount;
        this.consumedCount = consumedCount;
        this.messageCount = messageCount;
    }


    @Override
    public void run() {
        running = true;
        consume();
    }

    public void stop() {
        running = false;
    }

    public void consume() {
        while (running && consumedCount.get() < messageCount) {

            if (messageQueue.isEmpty()) {
                try {
                    messageQueue.waitProcessInProgress();
                } catch (InterruptedException e) {
                    log.severe("Error in consume messages.");
                    errorCount.incrementAndGet();
                    break;
                }
            }

            // avoid spurious wake-up
            if (!running) {
                break;
            }

            Message message = messageQueue.poll();
            useMessage(message);

            //Sleeping on random time to make it realistic
            ThreadUtil.sleep((long) (Math.random() * 100), errorCount);
        }
        log.info("Consumer Stopped");
    }

    private void useMessage(Message message) {
        if (message != null) {
            log.info(" Id: " + message.getId() + " Data: " + message.getData());
        }
        consumedCount.getAndIncrement();
    }

}