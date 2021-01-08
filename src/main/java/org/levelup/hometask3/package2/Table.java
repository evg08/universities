package org.levelup.hometask3.package2;

import org.levelup.hometask3.ReflectionClass;


public class Table {
    private long length;
    private long width;
    private long height;

    public Table() {
        this.length = 5;
        this.width = 6;
        this.height = 7;
    }

    @Override
    public String toString() {
        return "Table{" +
                "length=" + length +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
