package com.learning.assignment;

import com.learning.assignment.util.ThreadUtil;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class Producer implements Runnable {
    private static final Logger log = Logger.getLogger(Producer.class.getCanonicalName());
    private static final AtomicInteger idSequence = new AtomicInteger(0);
    private final MessageQueue messageQueue;
    private final AtomicInteger producedCount;
    private final AtomicInteger errorCount;
    private final int messageCount;
    private boolean running = false;

    public Producer(MessageQueue messageQueue, AtomicInteger producedCount, AtomicInteger errorCount, int messageCount) {

        this.messageQueue = messageQueue;
        this.errorCount = errorCount;
        this.producedCount = producedCount;
        this.messageCount = messageCount;
    }

    @Override
    public void run() {
        running = true;
        produce();
    }

    public void stop() {
        running = false;
    }

    public void produce() {

        while (running && producedCount.get() < messageCount) {

            if (messageQueue.isFull()) {
                try {
                    messageQueue.waitSpaceLeft();
                } catch (InterruptedException e) {
                    log.severe("Error in Produce messages.");
                    break;
                }
            }
            // avoid unwanted wake-up
            if (!running) {
                break;
            }

            messageQueue.add(generateMessage());
            producedCount.getAndIncrement();
            //Sleeping on random time
            ThreadUtil.sleep((long) (Math.random() * 100), errorCount);
        }

        log.info("Producer Stopped");
    }

    private Message generateMessage() {
        Message message = new Message(idSequence.incrementAndGet(), Math.random());
        log.info("Produced Message Id: " + message.getId() + " Data: " + message.getData());
        return message;
    }

}