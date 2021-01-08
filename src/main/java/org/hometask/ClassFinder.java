package org.hometask;

import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.pbkdf2.Integers;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class ClassFinder {
    public Collection <Class<?>> findAnnotatedClasses(String packagePath) {
        //target/classes/org
        //packagepath="org.hometask..."
        //src/main/java/org/hometask
        String rootPath = "src/main/java/";

        File root = new File(rootPath + packagePath.replace(".", "/"));
        if (!root.exists()) {
            throw new RuntimeException("Package with such name doesn't exost");
        }

        Collection<File> files=findFilesInFolder(root);
        System.out.println("List of files:");
        files.forEach( file -> System.out.println(file.getAbsolutePath())
        );
        Collection<String> classNames=files.stream()
                .map(file -> file.getAbsolutePath()) //данные о путях в файле
                .filter(filepath->filepath.contains(".java"))
                .map(filepath->{
                int startIndex=filepath.indexOf("\\java\\")+6;
                int endIndex =filepath.indexOf(".java");
             return  filepath.substring(startIndex,endIndex);
                })
                .map(filepath->filepath.replace("\\","."))
                .collect(Collectors.toList());
        System.out.println("List of class names");
        classNames.forEach(className-> System.out.println(className));
       return classNames.stream()
                .map(className->loadClass(className))
                .filter(clazz->isClassAnnotated(clazz))
                .collect(Collectors.toList());
         }

    //Ищем все файлы в папке (рекeрсивно -we find all files in folder and subfolders
    private Collection<File> findFilesInFolder(File folder) {
        Collection<File> files = new ArrayList<>();
        File[] filesInFolder = folder.listFiles();//list all files in folder
        if (filesInFolder == null) {
            return files;
        }
        for (File file : filesInFolder) {
            if (file.isDirectory()) { //если файл является попкой,то искать файлы в папке
                files.addAll(findFilesInFolder(file));
            } else {
                files.add(file);
            }
        }
        return files;
    }

    private Class<?> loadClass(String className){ //return any object of class
        try {
            return  Class.forName(className);
        }catch (ClassNotFoundException e){
            throw new RuntimeException("Couldn't load class by name",e);
        }
    }

    private boolean isClassAnnotated(Class<?> clazz)
    {
        return  clazz.isAnnotationPresent(ReflectClass.class);
        //clazz.getAnnotation()
    }



    private  void invokePrintCollection(){
        Collection <Integer>integers=new ArrayList<>();
        printCollection(integers);
    }
    private void printCollection(Collection<?> collection){

    }

}
