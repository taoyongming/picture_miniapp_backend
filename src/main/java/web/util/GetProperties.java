package web.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GetProperties {

	private static Properties config=null;

	public static void readAllProperties(String fileurl) {
		config = new Properties();
		try {
			InputStream in = GetProperties.class.getResourceAsStream(fileurl);
			config.load(in);
			in.close();
		} catch (IOException e) {
			System.out.println("no properties defined error");
		}
	}
	
	public static String getProperties(String key){
		if(null==config){
			System.out.println("----------获取配置文件信息开始---------");
			readAllProperties("/constant.properties");
			System.out.println("----------获取配置文件信息结束---------");
		}
		String value = config.getProperty(key);
		return value;
	}

	public Properties getConfig() {
		return config;
	}

	public void setConfig(Properties config) {
		GetProperties.config = config;
	}
	
	
	
}
