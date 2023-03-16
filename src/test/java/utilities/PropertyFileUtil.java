package utilities;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertyFileUtil {
public static String getValueForKey(String key)throws Throwable
{
	Properties conprop = new Properties();
	conprop.load(new FileInputStream("./PropertyFiles/Environment.properties"));
	return conprop.getProperty(key);
}
}
