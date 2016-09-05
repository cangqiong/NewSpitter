package com.bigdata.spitter.storage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.bigdata.spitter.Page;
import com.bigdata.spitter.util.FileUtils;

public class JsonStorage implements Storage {

	private static Logger logger = LoggerFactory.getLogger(JsonStorage.class);

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
							+ ".json")), "UTF-8"));
			printWriter.write(JSON.toJSONString(page.getFields()));
			printWriter.close();
		} catch (IOException e) {
			logger.warn("write file error", e);
		}
	}

}
