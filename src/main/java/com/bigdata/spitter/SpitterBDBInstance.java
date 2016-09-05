package com.bigdata.spitter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bigdata.spitter.db.BDBQueue;
import com.bigdata.spitter.processor.HtmlHandler;
import com.bigdata.spitter.scheduler.RequestQueue;
import com.bigdata.spitter.scheduler.ThreadLocalClientFactory;
import com.bigdata.spitter.storage.Storage;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * 爬虫实例
 * 
 * @author Cang
 *
 */
public class SpitterBDBInstance implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(SpitterBDBInstance.class);
    public static RequestQueue taskQueue = new BDBQueue();

    // 设置页面响应处理类
    private HtmlHandler handler = null;
    // 存储方法
    private Storage storage = null;

    public SpitterBDBInstance(HtmlHandler handler, Storage storage) {
	this.handler = handler;
	this.storage = storage;
    }

    @Override
    public void run() {
	while (true) {
	    Request request = taskQueue.getRequest();
	    fetchPage(request);
	}

    }

    /**
     * 使用请求抓取页面
     * 
     * @param request
     */
    private void fetchPage(Request request) {
	logger.info("current thread:" + Thread.currentThread().getName() + " catch url: "
		+ request.getUrl());
	HtmlPage htmlPage = null;
	// 获取一个webclient
	WebClient client = ThreadLocalClientFactory.getInstance().getClient();

	// 是否启用js
	client.getOptions().setJavaScriptEnabled(request.isJavaScriptEnabled());
	// 是否启用客户端重定向
	client.getOptions().setRedirectEnabled(request.isRedirectEnabled());
	// 设置超时
	client.getOptions().setTimeout(request.getTimeout());

	try {
	    htmlPage = client.getPage(request.getUrl());
	} catch (Exception e) {
	    logger.error("Fecth page error!");
	}

	Page page = handler.process(htmlPage);
	taskQueue.addRequest(page.getLinks());
	storage.process(page);
    }

}
