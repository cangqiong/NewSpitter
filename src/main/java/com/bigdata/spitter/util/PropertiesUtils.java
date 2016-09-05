package com.bigdata.spitter.util;

import static com.bigdata.spitter.util.PatternUtils.getPattern;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 加载properties文件，获取配置信息
 * 
 * @author Cang
 *
 */
public class PropertiesUtils {

	private static Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);
	private static Properties prop = null;

	/**
	 * 加载配置文件
	 */
	static {
		// 在静态代码块中加载配置文件
		InputStream in = PropertiesUtils.class.getClassLoader()
				.getResourceAsStream("config.properties");
		prop = new Properties();
		try {
			prop.load(in);
		} catch (IOException e) {
			logger.error("配置文件加载出错！");
			e.printStackTrace();
		}
	}

	/**
	 * 获取域名
	 * 
	 * @return
	 */
	public static String getDomain() {
		return (String) prop.get("domain");
	}

	/**
	 * 获取种子链接
	 * 
	 * @return
	 */
	public static String getStartUrl() {
		return (String) prop.get("startUrl");
	}

	/**
	 * 获取连接规则
	 * 
	 * @return 链接规则
	 */
	public static Pattern getLinkPattern() {
		String url = (String) prop.get("linkregex");
		return getPattern(url);
	}

	/**
	 * 获取爬虫线程数目
	 * 
	 * @return 链接规则
	 */
	public static int getThreadNum() {
		return Integer.parseInt((String) prop.get("threadnum"));
	}

}
