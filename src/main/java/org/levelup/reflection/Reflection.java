package org.levelup.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class Reflection {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        //1 var : есть объект нужного класс
        Phone phoneObject = new Phone();
        Class<?> phoneClassObject = phoneObject.getClass();// ?-wildcard
        System.out.println("Class neme:" + phoneClassObject.getName());

        //2 var
        Class<?> phClassObject = Phone.class;
        System.out.println("Two class object are " + (phoneClassObject == phClassObject));

        Class<?> intClassObject = int.class;
        System.out.println("Is Primitive()" + intClassObject.isPrimitive());
        Field[] declaredFields = phClassObject.getDeclaredFields();//getFields -return only public fields
        Field modelField = phClassObject.getDeclaredField("model");
        System.out.println("Field name" + modelField.getName());
        System.out.println("Field type" + modelField.getType()); //getType() возвращает Class<?>

        Phone samsung = new Phone("Samsung10", 4, 2.1);
        System.out.println();
        modelField.setAccessible(true);
        System.out.println("Field value:" + modelField.get(samsung));//get-получить значение поля (Field) у объекта ыфьыгтп
        modelField.set(samsung, "Samsung11"); //set-установить значение поля объекта
        System.out.println("Field value after changes:" + samsung.getModel());

        Constructor<?> constructor = phClassObject.getDeclaredConstructor(double.class);
        constructor.setAccessible(true);

        Phone createdWithPriveteConstuctor = (Phone) constructor
                .newInstance(2.3d);
        System.out.println("cpu from private constructor" + createdWithPriveteConstuctor.getCpu());
    }
}
