package org.levelup.thread.deadlock;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Transfer implements Runnable {

    private final Account first;
    private final Account second;
    private final TransferService service;
    private final double amount;


    @Override
    public void run() {
        service.transferMoney(first,second, amount);
    }
}
