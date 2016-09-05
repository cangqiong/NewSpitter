package com.bigdata.spitter.main;

import com.bigdata.spitter.BDBSpitter;
import com.bigdata.spitter.processor.ZhiLianHtmlHandler;
import com.bigdata.spitter.storage.NewAppendFileStorage;

/**
 * 程序入口
 *
 */
public class BDBZhiLianSpitter {

	public static void main(String[] args) {
		// turn off htmlunit warnings
		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
		java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);

		BDBSpitter spitter = new BDBSpitter(new ZhiLianHtmlHandler());
		spitter.setStorage(new NewAppendFileStorage());
		spitter.start();
	}
}
