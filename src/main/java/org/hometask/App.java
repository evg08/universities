package org.hometask;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

public class App {
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
      /* ClassLoader loader=Thread.currentThread().getContextClassLoader();
       ClassLoader mainLoader=App.class.getClassLoader();
        System.out.println(mainLoader==loader);
*/
        ClassFinder finder = new ClassFinder();
        Collection<Class<?>> classes = finder.findAnnotatedClasses("org.hometask.reflect");
        for (Class<?> clazz : classes) {
            Object object = clazz.getDeclaredConstructor().newInstance();
            System.out.println(object.toString());
        }
    }
}
