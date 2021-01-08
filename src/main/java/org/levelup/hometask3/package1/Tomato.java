package org.levelup.hometask3.package1;

import org.levelup.hometask3.ReflectionClass;


@ReflectionClass
public class Tomato {
    public int weight;
    public String colour;

    public Tomato() {
        this.weight = 500;
        this.colour = "red";
    }

    @Override
    public String toString() {
        return "Tomato{" +
                "weight=" + weight +
                ", colour='" + colour + '\'' +
                '}';
    }


}
