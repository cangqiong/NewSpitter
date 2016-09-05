package com.bigdata.spitter.main;

import com.bigdata.spitter.Spitter;
import com.bigdata.spitter.processor.SimpleHtmlHandler;
import com.bigdata.spitter.storage.AppendFileStorage;

/**
 * 程序入口
 *
 */
public class App {

	public static void main(String[] args) {
		// turn off htmlunit warnings
		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
		java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);

		Spitter spitter = new Spitter(new SimpleHtmlHandler());
		spitter.setStorage(new AppendFileStorage());
		spitter.start();
	}
}
