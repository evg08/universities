package org.levelup.thread.queue;

import java.util.LinkedList;

public class BlockingQueue implements Queue {

    private final LinkedList<Runnable> tasks;
    private final int capacity; //how many tasks in queue
    private final Object fullQueue = new Object();
    private final Object emptyQueue = new Object();

    public BlockingQueue(int capacity) {
        this.tasks = new LinkedList<>();
        this.capacity = capacity;

    }

    @Override
    public void add(Runnable runnable) throws InterruptedException {
        //t1 взял монитор,очередь заполнена,t1 освободил монитор и ушел в wait
        synchronized (fullQueue) {
            while (tasks.size() == capacity) { //case ,when queue is full
                //wait(1000)
                //sleep(1000)
                fullQueue.wait();
            }
        }
        synchronized (emptyQueue) {

            tasks.addLast(runnable);//no place in queue
          //  emptyQueue.notifyAll(); //разбудили потоки,которые ожидали добавления задачи в очередь
            emptyQueue.notify();
        }
    }

    @Override
    public Runnable take() throws InterruptedException {
        synchronized (emptyQueue){
            while (tasks.isEmpty()){
                emptyQueue.wait();
            }
        }
        synchronized (fullQueue) {
            Runnable task = tasks.poll(); //null if queue is empty
          //  fullQueue.notifyAll();
            fullQueue.notify();
            return task;
        }
    }
}
