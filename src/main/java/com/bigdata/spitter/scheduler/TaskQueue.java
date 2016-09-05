package com.bigdata.spitter.scheduler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bigdata.spitter.Request;
import com.bigdata.spitter.robots.Robots;

/**
 * 任务队列
 * 
 * @author Cang
 *
 */
public class TaskQueue implements RequestQueue, LinkFilter {

    private static Logger logger = LoggerFactory.getLogger(TaskQueue.class);
    // 链接队列,FIFO
    private static LinkedBlockingQueue<Request> requestQueue = new LinkedBlockingQueue<Request>();
    // bloom过滤器,过滤重复连接
    private static SimpleBloomFilter bloomFilter = new SimpleBloomFilter();
    // 过滤文件格式数组
    private static String[] htmlFomatArrary = new String[] { ".jpg", ".png", ".gif", ".csv",
	    ".pdf", ".doc", ".zip", ".gz", ".rar", ".exe", ".tar", ".chm", ".iso" };

    /**
     * 添加请求
     * 
     * @param links 请求列表
     */
    public boolean addRequest(String url) {
	try {
	    if (accept(url)) {
		requestQueue.put(Request.warpper(url));
	    }
	    return true;
	} catch (InterruptedException e) {
	    logger.error("Add request interrupted exception");
	    return false;
	}
    }

    /**
     * 获取请求
     */
    public Request getRequest() {
	logger.info("Get url, the queue size is " + size());
	Request request = null;
	try {
	    request = requestQueue.take();
	} catch (InterruptedException e) {
	    logger.error("Get request interrupted exception!");
	}
	return request;
    }

    /**
     * 添加请求
     * 
     * @param links url列表
     */
    public void addRequest(List<String> links) {
	for (String link : links) {
	    addRequest(link);
	}
    }

    /**
     * 判断请求队列是否为空
     * 
     * @return
     */
    public boolean isEmpty() {
	return requestQueue.isEmpty();
    }

    /**
     * 获取请求队列的长度
     * 
     * @return
     */
    public int size() {
	return requestQueue.size();
    }

    @Override
    public boolean accept(String url) {
	try {
	    if (Robots.isRobotAllowed(new URL(url))) {
		if (filterFormat(url)) {
		    return bloomFilter.contains(url);
		}
	    }
	} catch (MalformedURLException e) {
	    logger.error("Link transform to URL error!");
	    return false;
	}
	return false;
    }

    /**
     * 过滤无效的网页格式
     * 
     * @param url
     * @return
     */
    private boolean filterFormat(String url) {
	for (String htmlFormat : htmlFomatArrary) {
	    if (url.endsWith(htmlFormat)) {
		return false;
	    }
	}
	return true;
    }
}
