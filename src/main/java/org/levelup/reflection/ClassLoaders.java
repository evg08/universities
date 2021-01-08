package org.levelup.reflection;

import org.levelup.universities.jdbc.App;
import org.levelup.universities.jdbc.FacultyJdbsStorage;

public class ClassLoaders {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        String s="";
        ClassLoader load =s.getClass().getClassLoader();
        System.out.println(load); //s was uploaded Bootstrap
        ClassLoader appClassLoadr=App.class.getClassLoader();
        System.out.println(appClassLoadr);
        //upload class
        Class.forName("org.postgresql.Driver");//for jdbc <4
        Class classTest=Class.forName("org.levelup.universities.jdbc.FacultyJdbsStorage");//for jdbc <4
       //create bew instance of class
        FacultyJdbsStorage d = (FacultyJdbsStorage) classTest.newInstance();
        d.displayFacilties();



    }
}
