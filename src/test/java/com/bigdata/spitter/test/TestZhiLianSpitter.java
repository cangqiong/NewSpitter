package com.bigdata.spitter.test;

import com.bigdata.spitter.Spitter;
import com.bigdata.spitter.processor.ZhiLianHtmlHandler;
import com.bigdata.spitter.storage.NewAppendFileStorage;

/**
 * 程序入口
 *
 */
public class TestZhiLianSpitter {

	public static void main(String[] args) {
		// turn off htmlunit warnings
		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
		java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);

		Spitter spitter = new Spitter(new ZhiLianHtmlHandler());
		spitter.setStorage(new NewAppendFileStorage());
		spitter.setStartUrl("http://jobs.zhaopin.com/000100540258245.htm?ff=100&ss=301");
		spitter.start();
	}
}
