package org.levelup.thread.queue;

public class ProducerConsumerApp {
    public static void main(String[] args) {
        Queue queue=new BlockingQueue(6);
        Producer p1=new Producer(queue);
        Producer p2=new Producer(queue);
        new Thread(p1,"producer-1").start();
        new Thread(p2,"producer-2").start();

        new Thread(new Consumer(queue),"consumer-1").start();
        new Thread(new Consumer(queue),"consumer-2").start();
    }
}
