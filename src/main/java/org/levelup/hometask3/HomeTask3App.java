package org.levelup.hometask3;

import java.io.IOException;

public class HomeTask3App {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, IOException, InstantiationException {
        String packageName="org.levelup.hometask3.package1";
        ServiceHomeTask3.findAndCreateAllClassesWithAnnotation(packageName);
    }
}
