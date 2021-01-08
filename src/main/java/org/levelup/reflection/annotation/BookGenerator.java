package org.levelup.reflection.annotation;

import java.util.ArrayList;
import java.util.Collection;


public class BookGenerator {

    private RandomIntAnnotationProcessor procesor;
    public BookGenerator(){
        this.procesor = new RandomIntAnnotationProcessor();
    }


    public Collection<Book> generateBook(int count){
        Collection<Book> book = new ArrayList<>();
        for (int i=0;i<count;i++){

            book.add(procesor.createAndProcess(Book.class));
        }
        return book;
    }





}
