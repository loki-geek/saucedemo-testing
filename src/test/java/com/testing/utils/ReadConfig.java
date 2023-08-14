package com.testing.utils;

import java.io.FileInputStream;
import java.util.Properties;

public class ReadConfig {
	//reading the data from the config.properties file.
	public Properties prop;
	public ReadConfig() {
		
		try {
			FileInputStream configFile = new FileInputStream(System.getProperty("user.dir")+"\\configuration\\config.properties");
			prop = new Properties();
			prop.load(configFile);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public String readData(String keyName) {
		//getting the value from the config file based on the keyname.
		return prop.getProperty(keyName);
	}

}
