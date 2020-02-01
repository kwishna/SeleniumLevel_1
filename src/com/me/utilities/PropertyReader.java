package com.me.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {
	
	private String fileName;
	
	public PropertyReader(String fileName) {
		
			this.fileName = fileName;
		
	}
	
	public String getValue(String key) throws FileNotFoundException, IOException {
		
		Properties prop = new Properties();
		prop.load(new FileInputStream(System.getProperty("user.dir")+"/Resources/config/"+fileName));
		return prop.getProperty(key);
		
	}
	
}
