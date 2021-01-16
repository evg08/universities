package org.levelup.thread.queue;

public interface Queue {
    //add to queue
    void add (Runnable runnable) throws InterruptedException;

    //take from queue
    Runnable take() throws InterruptedException;
}
