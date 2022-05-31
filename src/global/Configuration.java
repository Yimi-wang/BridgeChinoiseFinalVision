package global;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.logging.Logger;

public class Configuration {
	private static Configuration instance = null;
	Properties prop;

	private Configuration() {
		prop = new Properties();
		try {
			File file = new File("./res/default.cfg");
			InputStream propIn = new FileInputStream(file);
			prop.load(propIn);
		}catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public static Configuration instance() {
		//if(instance == null)
			instance = new Configuration();
		return instance;
	}
	
	public String lis(String cle) throws NoSuchElementException{
		String res = prop.getProperty(cle);
		if(res == null) throw new NoSuchElementException("Prop " + cle + " not defined");
		return res;
	}

	
} 
