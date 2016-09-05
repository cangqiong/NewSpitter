package com.bigdata.spitter.storage;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bigdata.spitter.Page;
import com.bigdata.spitter.util.FileUtils;

/**
 * 新的文件添加存储类,改变了存储的形式
 * 
 * @author Cang
 *
 */
public class NewAppendFileStorage implements Storage {

    private Logger logger = LoggerFactory.getLogger(NewAppendFileStorage.class);
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
	    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
		    FileUtils.getFile(fullname), true), "UTF-8"));
	    for (Object value : page.getFields().values()) {
		out.write(value.toString());
	    }
	    out.write("\n");
	    out.close();
	} catch (IOException e) {
	    logger.warn("write file error", e);
	}
    }
}
