/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munsch.feedreader.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Munsch
 */
public class PropsManager {
    private final String configFile = "config.properties";
    private static final PropsManager singleton = new PropsManager( );

       private PropsManager() { }
    
    public static PropsManager getInstance( ) {  return singleton;  }
    
    public String getProperty(String property) throws IOException {
                System.out.println("WTF1");
        Properties prop = new Properties();
        System.out.println("WTF2");
        try (InputStream in = getClass().getResourceAsStream(configFile)) {
            System.out.println("WTF3");
            prop.load(in);
        }
        System.out.println("WTF4");
        return prop.getProperty(property);
    }
    
    public void setProperty(String property, String value) throws FileNotFoundException, IOException {
        Properties props;
        try (FileInputStream in = new FileInputStream(configFile)) {
            props = new Properties();
            props.load(in);
        }
        try (FileOutputStream out = new FileOutputStream(configFile)) {
            props.setProperty(property, value);
            props.store(out, null);
        }
    }
}
