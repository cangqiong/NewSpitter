package com.bigdata.spitter.storage;

import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bigdata.spitter.Page;

public class ConsoleStorage implements Storage {

	private static Logger logger = LoggerFactory
			.getLogger(ConsoleStorage.class);

	@Override
	public void process(Page page) {
		if (page == null) {
			System.out.println("page is null");
			return;
		}
		for (Entry<String, Object> map : page.getFields().entrySet()) {
			System.out.println(map);
		}
	}

}
