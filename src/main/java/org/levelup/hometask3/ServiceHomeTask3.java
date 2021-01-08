package org.levelup.hometask3;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceHomeTask3 {

    static void findAndCreateAllClassesWithAnnotation(String packageName) throws ClassNotFoundException, IllegalAccessException, InstantiationException, IOException {
        //get list of all classes in package
        List<Class> classList = getClasses(packageName);
        if (classList.size() == 0) {
            System.out.println("No classes in package");
            return;
        }
        //get list of classes that are not interfaces and contains ReflectionClass annotation
        List<Class> listWithAn = classList.stream().filter(cl -> cl.getAnnotation(ReflectionClass.class) != null).filter(cl -> !cl.isInterface()).collect(Collectors.toList());
        if (listWithAn.size() == 0) {
            System.out.println("No classes in package with Annotation");
            return;
        }

        //create of new instances and print them
        listWithAn.stream().forEach(cl -> {
            try {
                System.out.println(cl.newInstance().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private static List<Class> getClasses(String packageName)
            throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class> classes = new ArrayList<Class>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes;
    }

    private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<Class>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }

}
