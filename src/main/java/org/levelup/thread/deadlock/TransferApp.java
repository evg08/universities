package org.levelup.thread.deadlock;

public class TransferApp {
    public static void main(String[] args) throws InterruptedException {

        Account a = new Account("1234 - 1234", 40000.56d);
        Account b = new Account("12345 - 12345", 50000.78d);
        TransferService service = new TransferService();


        for (int i =0; i<1000; i++) {
            new Thread(new Transfer(a, b, service, 540)).start();
            new Thread(new Transfer(b, a, service, 340)).start();

            //Thread.sleep(40);
            System.out.println(i);

        }
        Thread.sleep(5000);
        System.out.println(a.getAmount());
        System.out.println(b.getAmount());

    }
}
