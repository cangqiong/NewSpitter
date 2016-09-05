package com.bigdata.spitter.util;

import java.util.regex.Pattern;

/**
 * 模式工具类
 * 
 * @author cang
 *
 */
public class PatternUtils {

	/**
	 * 获取正则模式,可以进行自我定制
	 * 
	 * @param 普通链接
	 * @return
	 */
	public static Pattern getPattern(String url) {
		String regexStr = null;
		// 数字转义
		regexStr = url.replace("{%d}", "\\d{1,18}");

		// 将问号转义
		regexStr = regexStr.replace("?", "\\?");
		// 将点号转义
		regexStr = regexStr.replace(".", "\\.");

		regexStr += "([\\s\\S]*)";
		return Pattern.compile(regexStr);
	}

}
