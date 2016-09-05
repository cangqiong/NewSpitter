package com.bigdata.spitter.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;

public class FileUtils {
	private final static int DEFAULT_BUFFER_SIZE = 1024;

	/**
	 * 文本文件转换为指定编码的字符串
	 * 
	 * @param file
	 *            文本文件
	 * @param encoding
	 *            编码类型
	 * @return 转换后的字符串
	 * @throws IOException
	 */
	public static String file2String(File file, String encoding) {
		InputStreamReader reader = null;
		StringWriter writer = new StringWriter();
		try {
			if (encoding == null || "".equals(encoding.trim())) {
				reader = new InputStreamReader(new FileInputStream(file));
			} else {
				reader = new InputStreamReader(new FileInputStream(file),
						encoding);
			}
			// 将输入流写入输出流
			char[] buffer = new char[DEFAULT_BUFFER_SIZE];
			int n = 0;
			while (-1 != (n = reader.read(buffer))) {
				writer.write(buffer, 0, n);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		// 返回转换结果
		if (writer != null)
			return writer.toString();
		else
			return null;
	}

	public static File getFile(String fullName) {
		checkAndMakeParentDirecotry(fullName);
		return new File(fullName);
	}

	public static void checkAndMakeParentDirecotry(String fullName) {
		int index = fullName.lastIndexOf(File.separator);
		if (index > 0) {
			String path = fullName.substring(0, index);
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
		}
	}
}
