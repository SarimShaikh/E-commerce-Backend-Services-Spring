package com.inventory.sys.utils;

import org.springframework.core.io.UrlResource;


import java.nio.file.Path;
import java.nio.file.Paths;

public class UtilsClass {

    public static String getImageUrl(String path) throws Exception{
        Path root = Paths.get(path);
        Path file = root.resolve(path);
        String resource = new UrlResource(file.toUri()).toString();
        resource = resource.replaceAll("\\[", "").replaceAll("\\]","");
        return resource;
    }
}
