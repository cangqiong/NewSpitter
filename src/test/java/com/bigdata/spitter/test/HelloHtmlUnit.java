package com.bigdata.spitter.test;

import java.util.List;

import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class HelloHtmlUnit {
	public static void main(String[] args) throws Exception {
		String url = "http://jobs.zhaopin.com/141087207250569.htm";
		
		// 创建一个webclient
		WebClient webClient = new WebClient();
		// htmlunit 对css和javascript的支持不好，所以请关闭之
		// 1、启用js
		webClient.getOptions().setJavaScriptEnabled(true);
		// 2 、禁用css
		webClient.getOptions().setCssEnabled(false);
		// 3 、启动客户端重定向
		webClient.getOptions().setRedirectEnabled(true);

		// 4、js出错，是否抛出异常
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		// 5、设置超时
		webClient.getOptions().setTimeout(50000);

		HtmlPage htmlPage = webClient.getPage(url);
		// 6、等待js渲染后获取最终页面
		webClient.waitForBackgroundJavaScript(10000);

		List<HtmlAnchor> anchors = htmlPage.getAnchors();
		System.out.println(anchors.size());
		for (HtmlAnchor anchor : anchors) {
			System.out.println(anchor.getHrefAttribute());
		}
		String str;
		// 获取页面的TITLE
//		str = htmlPage.getTitleText();
//		System.out.println(str);
		// 获取页面的XML代码
		str = htmlPage.asXml();
		System.out.println(str);
		// 获取页面的文本
//		str = htmlPage.asText();
//		System.out.println(str);
		// 关闭webclient
		webClient.close();
	}
}