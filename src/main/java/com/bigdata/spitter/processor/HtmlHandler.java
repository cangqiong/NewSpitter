package com.bigdata.spitter.processor;

import com.bigdata.spitter.Page;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * HTML解析接口
 * @author Cang
 *
 */
public interface HtmlHandler {
	
	/**
	 * 解析HTML页面并返回所需的对象
	 * @param html
	 * @return
	 */
	Page process(HtmlPage html);
}
