package org.levelup.thread.flowerbed;

import java.util.HashMap;

public class FlowerBed implements Flower {
    private final int capacity;
    private final HashMap<Integer, Boolean> flowersMap;


    public FlowerBed(int capacity) {
        this.capacity = capacity;
        flowersMap = new HashMap<>();
        setAllDry();
        System.out.println("------------------->All Flowers became dry");
    }

    private void setAllDry() {
        for (int i = 0; i < capacity; i++) {
            flowersMap.put(i, false);
        }
    }


    private int findFirstDryFlower() {
        for (int i = 0; i < capacity; i++) {
            if (!flowersMap.get(i)) return i;
        }
        return -1;
    }


    @Override
    public void setAllDryState() {
        synchronized (flowersMap) {
            setAllDry();
            System.out.println("------------------->All Flowers became dry");
        }
    }

    @Override
    public void setOneWetState() {
        synchronized (flowersMap) {
            int key = findFirstDryFlower();
            if (key >= 0 && (key < capacity)) {
                flowersMap.replace(key, true);
                System.out.println("------------------->Flower " + key + " became wet");
            } else System.out.println("------------------->All Flowers became wet and Gardenman does nothing");
        }
    }
}
