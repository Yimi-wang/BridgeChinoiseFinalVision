package global;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.logging.Logger;

public class ConfigurationSetting {
    private static ConfigurationSetting instance = null;
    Properties prop;

    private ConfigurationSetting() {
        prop = new Properties();
        try {
            File file = new File("./res/defaultSetting.cfg");
            InputStream propIn = new FileInputStream(file);
            prop.load(propIn);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ConfigurationSetting instance() {
        instance = new ConfigurationSetting();
        return instance;
    }

    public String lis(String cle) throws NoSuchElementException{
        String res = prop.getProperty(cle);
        if(res == null) throw new NoSuchElementException("Prop " + cle + " not defined");
        return res;
    }


}
