package com.bigdata.spitter.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.w3c.dom.Element;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * HtmlUnit解析抽取工具类
 * 
 * @author Cang
 *
 */
public class HtmlunitUtils {

	/**
	 * 获取a标签的链接
	 * 
	 * @param html
	 *            HTML字符串
	 * @return
	 */
	public static List<String> getALinks(HtmlPage html) {
		List<String> linkList = new ArrayList<String>();
		List<HtmlAnchor> anchors = html.getAnchors();

		for (HtmlAnchor anchor : anchors) {
			linkList.add(anchor.getHrefAttribute());
		}
		return linkList;
	}

	/**
	 * 获取固定格式a标签的链接
	 * 
	 * @param html
	 *            HTML字符串
	 * @param pattern
	 *            要匹配的模式
	 * @return
	 */
	public static List<String> getALinksByregex(HtmlPage html, Pattern pattern) {
		List<String> linkList = new ArrayList<String>();
		List<HtmlAnchor> anchors = html.getAnchors();

		String temp = "";
		for (HtmlAnchor anchor : anchors) {
			temp = anchor.getHrefAttribute();
			if (pattern.matcher(temp).find()) {
				linkList.add(temp);
			}
		}
		return linkList;
	}
}
