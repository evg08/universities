package org.levelup.thread.flowerbed;

import lombok.SneakyThrows;

public class GardenMan  implements Runnable{
    private String name;
    private final Flower queue;

    public GardenMan(String name, Flower queue) {
        this.name = name;
        this.queue = queue;
    }

    @SneakyThrows
    @Override
    public void run() {
        while (true){
            System.out.println("Person with name" +name);
            queue.setOneWetState();
            Thread.sleep(200);
        }

    }
}
