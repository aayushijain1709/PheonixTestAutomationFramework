package com.api.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
    /* Properties is a class in java to read data from property file
    Reason 1: One config for the whole test run  Configuration is global and read-only You don’t want multiple
instances loading the same file. Static = single shared state per JVM If it were non-static: Every test class
could create its own ConfigManager Config file could be loaded multiple times Risk of inconsistency and wasted I/O
Static ensures singleton-like behavior without boilerplate */
    /*static {
    Properties properties = new Properties(); // local variable }
This would: Create a temporary object. Lose reference once method exits Make get() impossible (nothing to read from)*/
    private static Properties properties = new Properties();
//static bloc will run only once 1st time when class loaded in memory.
    static {
        FileReader reader;
        //It as load method that can be used to load the data of the property file but that method require file reader
       // File configFile = new File(System.getProperty("user.dir")+ "\\src\\test\\resources\\config\\config.properties");
    File configFile = new File(System.getProperty("user.dir")+File.separator+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"config"+File.separator+"config.properties");


    try {
            reader = new FileReader(configFile);
            properties.load(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static String getProperty( String key)  {
        return  properties.getProperty(key);

    }
}
