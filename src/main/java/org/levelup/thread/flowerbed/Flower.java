package org.levelup.thread.flowerbed;

public interface Flower {

    void setAllDryState() throws InterruptedException;

    void setOneWetState() throws InterruptedException;
}
