package com.inventory.sys.utils;

public class UtilsClass {

    public static String getExtention(String fileName){
        String extension = ".";
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension += fileName.substring(i+1);
        }
        return extension;
    }
}
