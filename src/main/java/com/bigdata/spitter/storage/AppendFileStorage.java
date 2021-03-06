package com.bigdata.spitter.storage;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
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

public class AppendFileStorage implements Storage {

	private Logger logger = LoggerFactory.getLogger(AppendFileStorage.class);
	private String fileName = null;

	@Override
	public void process(Page page) {
		if (page == null) {
			return;
		}
		if (fileName == null) {
			// 获得当前时间
			DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			// 作为随机名称
			fileName = format.format(new Date());
		}
		String fullname = this.path + "/" + fileName;
		if (page.getFields().get("jobname") == null) {
			return;
		}
		try {
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(FileUtils.getFile(fullname), true),
					"UTF-8"));
			for (Map.Entry<String, Object> entry : page.getFields().entrySet()) {
				if (entry.getValue() instanceof Iterable) {

					Iterable value = (Iterable) entry.getValue();
					out.write(entry.getKey() + ":");
					for (Object o : value) {
						out.write(o.toString());
					}
				} else {
					out.write(entry.getKey() + ":" + entry.getValue());
				}
				out.write("\t");
			}
			out.write("\n");
			out.close();
		} catch (IOException e) {
			logger.warn("write file error", e);
		}
	}
}
