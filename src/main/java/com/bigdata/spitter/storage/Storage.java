package com.bigdata.spitter.storage;

import com.bigdata.spitter.Page;

/**
 * 存储接口
 * 
 * @author Cang
 *
 */
public interface Storage {
	final static String path = "/tmp/NewSpitter";
	public void process(Page page);
}
