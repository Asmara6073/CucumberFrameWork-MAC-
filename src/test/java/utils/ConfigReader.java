package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    static Properties prop;
    // this method will read any property file
    public static Properties readProperties(String filePath){// first we need the path of the property file
        try {
            FileInputStream fis=new FileInputStream(filePath);// FIS allows us to navigate to the location of the file
            prop=new Properties();//special class to read .properties files
            prop.load(fis);// load all the data from the property file
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return prop;
    }

    // this method will get single value based on the key
    public static String getPropertyValue(String key){

        // returns the value at the spefified key in a property file
        return prop.getProperty(key);
    }




}
