package utilities;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertyFileUtil {

	public static String getValueForKey(String key)throws Throwable
	{
		Properties conpro = new Properties();
		conpro.load(new FileInputStream("./PropertyFiles//Environment.propertie"));
		return conpro.getProperty(key);
	}
}
