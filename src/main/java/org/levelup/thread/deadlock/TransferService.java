package org.levelup.thread.deadlock;

public class TransferService {
    public void transferMoney(Account a,Account b, double amount){
        //if a>b then first always a how to avoid dealock
      Account first=a.getAccountId().compareTo(b.getAccountId())>=0? a:b;
      Account second=first==a?b:a;
        synchronized (a){
            synchronized (b){
                a.setAmount(a.getAmount()-amount);
                b.setAmount(b.getAmount()-amount);
            }
        }
    }
}
