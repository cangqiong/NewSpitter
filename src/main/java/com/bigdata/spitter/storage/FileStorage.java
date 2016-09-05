package com.bigdata.spitter.storage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bigdata.spitter.Page;
import com.bigdata.spitter.util.FileUtils;

public class FileStorage implements Storage {

	private static Logger logger = LoggerFactory.getLogger(FileStorage.class);

	@Override
	public void process(Page page) {
		if (page == null) {
			return;
		}
		try {
			// 获得当前时间
			DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			// 作为随机名称
			String fileName = format.format(new Date());
			PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(
					new FileOutputStream(FileUtils.getFile(path + fileName
							+ ".html")), "UTF-8"));
			for (Map.Entry<String, Object> entry : page.getFields().entrySet()) {
				if (entry.getValue() instanceof Iterable) {
					Iterable value = (Iterable) entry.getValue();
					printWriter.println(entry.getKey() + ":");
					for (Object o : value) {
						printWriter.println(o);
					}
				} else {
					printWriter.println(entry.getKey() + ":\t"
							+ entry.getValue());
				}
			}
			printWriter.close();
		} catch (IOException e) {
			logger.warn("write file error", e);
		}
	}

}
