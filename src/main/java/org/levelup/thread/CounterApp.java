package org.levelup.thread;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;


public class CounterApp {
    public static void main(String[] args) throws InterruptedException {
        Counter counter=new Counter();
        Thread t1=new Thread(new CountWorker(counter),"t1");
        Thread t2=new Thread(new CountWorker(counter),"t2");
        Thread t3=new Thread(new CountWorker(counter),"t3");
        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("Total count"+ counter.getValue());

    }


    static class Counter{
        private int value;
       // public  synchronized void increment(){
            public   void increment(){
                synchronized (this){
            //t1.readvalue=10
            //t2.readvalue=10
            //t1.writevalue=11
            //t2.writevalue=11
            value++;}
        }

        public synchronized int getValue(){
            return value;
        }
    }

    @AllArgsConstructor
    static class CountWorker implements Runnable{
        private Counter counter;

        @SneakyThrows
        @Override
        public void run() {
            for (int i=0;i<20;i++){
                counter.increment();
                System.out.println(Thread.currentThread().getName()+"  "+counter.getValue());
                Thread.sleep(100);
            }

        }
    }
}
