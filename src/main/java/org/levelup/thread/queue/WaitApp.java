package org.levelup.thread.queue;

public class WaitApp {
    public static void main(String[] args) throws InterruptedException {
        Object object=new Object();
        object.wait();// illegal exception
    }
}
