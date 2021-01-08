package org.levelup.reflection.annotation;

import java.util.Collection;

public class App {
    public static void main(String[] args) {
        BookGenerator generator = new BookGenerator();

        Collection<Book> collection = generator.generateBook(10);
        collection.forEach(book-> System.out.println(book));
    }
}
