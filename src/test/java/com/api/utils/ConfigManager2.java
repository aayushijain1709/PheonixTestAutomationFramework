package com.api.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager2 {
    private static Properties properties = new Properties();
    private static String filePath ="config/config.properties";
    private static String env;
//static bloc will run only once 1st time when class loaded in memory.
    static {
       env= System.getProperty("env","qa"); // if env not passed it will take default value as qa
       env.toLowerCase().trim();
       filePath = "config/config-"+env+".properties";
    System.out.println("File Path :"+filePath);
    InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
       if(input==null)
       {
           throw new RuntimeException("cant find the file path");
       }
        try {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static String getProperty( String key)  {
        return  properties.getProperty(key);

    }
}
