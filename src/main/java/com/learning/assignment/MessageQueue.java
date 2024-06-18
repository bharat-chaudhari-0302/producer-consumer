package com.learning.assignment;

import java.util.LinkedList;
import java.util.Queue;

public class MessageQueue {
    private final Queue<Message> queue = new LinkedList<>();
    private final int maxSize;
    private final Object spaceLeft = new Object();
    private final Object processInProgress = new Object();

    public MessageQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    public boolean isFull() {
        return queue.size() == maxSize;
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public void waitSpaceLeft() throws InterruptedException {
        synchronized (spaceLeft) {
            spaceLeft.wait();
        }
    }

    public void waitProcessInProgress() throws InterruptedException {
        synchronized (processInProgress) {
            processInProgress.wait();
        }
    }

    public void add(Message message) {
        queue.add(message);
        notifyProcessInProgress();
    }

    public Message poll() {
        Message mess = queue.poll();
        notifySpaceLeft();
        return mess;
    }

    public Integer getSize() {
        return queue.size();
    }

    private void notifySpaceLeft() {
        synchronized (spaceLeft) {
            spaceLeft.notify();
        }
    }

    private void notifyProcessInProgress() {
        synchronized (processInProgress) {
            processInProgress.notify();
        }
    }
}