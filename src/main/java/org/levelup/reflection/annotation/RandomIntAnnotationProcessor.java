package org.levelup.reflection.annotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public class RandomIntAnnotationProcessor {

    public <T> T createAndProcess(Class<T> tClass) { //входной параметр тип,на выходе объект класса этого типа
        // у класс T должен быть обязательно конструктор без параметров

        try {
            Constructor<T> constructor = tClass.getDeclaredConstructor(); //конструктор без параметров
            T instanse = constructor.newInstance();// создали объект Т через рефлексию

            //Найт все поля отмеченные аннотацией
            Field[] fields = tClass.getDeclaredFields();

            for (Field field:fields){
                RandomInt annotation = field.getAnnotation(RandomInt.class);
                if (annotation!=null){
                    int randomInt = generateRandomInteger(annotation.min(), annotation.max());

                    //записать знание в поле
                    field.setAccessible(true);
                    field.set(instanse, randomInt);
                }
            }

            return instanse;
        }catch(Exception exp){
            throw new RuntimeException();
        }
    }

    private int generateRandomInteger(int min, int max) {
        return new Random().nextInt(max - min) + min; //
    }

}

