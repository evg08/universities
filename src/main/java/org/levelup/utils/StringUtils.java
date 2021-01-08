package org.levelup.utils;

public class StringUtils {
    public static  boolean isBlank(String value){
        //true if string is null or spaces
        // null, "","   "
        //trim-delete spaces in the begin and and of string
        return value==null || value.trim().isEmpty();
    }
}
