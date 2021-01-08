package org.levelup.thread;

public class ThreadApp {

    public static void main(String[] args) throws InterruptedException {
        //extends class Thread
        //implement interface Runnable
        //implement interface Callable
        FirstThread t=new FirstThread();
        t.setName("first-thread");
        t.start();//запускаем поток

        Thread runnable= new Thread(new PrintHelloWorldRunnable(),"runnable-thread");
        runnable.start();

        t.join();//main лжидать пока поток t не завершит свое выполнение
        runnable.join();



        System.out.println("end of  "+Thread.currentThread().getName());
    }
        static class FirstThread extends Thread {

            @Override
            public void run() {
                for (int i=0;i<10;i++){
                  //  System.out.println( Thread.currentThread().getName()+"  " +i);
                    System.out.println( getName()+"  " +i);
                }
            }
        }

        static  class PrintHelloWorldRunnable implements Runnable{

            @Override
            public void run() {
                for (int i=0;i<10;i++){
                   System.out.println( Thread.currentThread().getName()+ " Hello world");

                }
            }
        }
}
