package org.levelup.thread.flowerbed;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class Flowers implements Runnable {
    private final Flower queue;

    @SneakyThrows
    @Override
    public void run() {
        while (true) {
            System.out.println("Flowers");
            queue.setAllDryState();
            Thread.sleep(1000);
        }
    }
}
