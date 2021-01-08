package org.levelup.reflection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookService {
    private int getBookdMethodeInvokerCounter=0;
    public List<String> getBooks() {
        Long startTime=System.nanoTime();
        getBookdMethodeInvokerCounter++;
        System.out.println("getBooks() methode is invoked");
        List<String> books =new ArrayList<>(Arrays.asList("Book1", "Book2", "Book3"));
        System.out.println("Execute time:"+(System.nanoTime()-startTime));
        return books;
    }
}
