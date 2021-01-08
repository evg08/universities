package org.levelup.reflection.annotation;

public class Book {

    @RandomInt
    private  int bookNumber;

    @RandomInt(min=10, max= 2000)
    private  int pageCount;

    private int symbolCount;


    @Override
    public String toString() {
        return "Book{" +
                "bookNumber=" + bookNumber +
                ", pageCount=" + pageCount +
                ", symbolCount=" + symbolCount +
                '}';
    }
}
