package org.levelup.hometask3.package1;

import org.levelup.hometask3.ReflectionClass;

@ReflectionClass
public class Apple {
    private int weight;
    private String colour;

    public Apple() {
        this.weight = 350;
        this.colour = "green";
    }

    @Override
    public String toString() {
        return "Apple{" +
                "weight=" + weight +
                ", colour='" + colour + '\'' +
                '}';
    }
}
