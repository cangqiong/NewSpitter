package com.bigdata.spitter;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 封装请求信息
 * 
 * @author Cang
 *
 */

@Setter
@Getter
public class Request implements Serializable {

    private static final long serialVersionUID = 2950187079376326883L;

    // 请求链接
    private String url;

    // 是否启用JS
    private boolean javaScriptEnabled = false;

    // 是否页面进行重定向
    private boolean redirectEnabled = true;

    // 页面超时时间
    private int timeout = 50000;

    public Request() {
    }
    
    public Request(String url) {
	this.url = url;
    }

    /**
     * 根据相关url链接改变client设置
     * 
     * @param url
     */
    public static Request warpper(String url) {
	url = transformLink(url);
	Request req = new Request(url);
	if (url.startsWith("http://jobs.zhaopin.com")) {
	    req.javaScriptEnabled = true;
	}
	return req;
    }

    /**
     * 将链接的无效消息去除，防止重复连接
     * 
     * @param url 原始链接
     * @return 有效链接
     */
    public static String transformLink(String url) {
	if (url.indexOf("#") > 0) {
	    return url.substring(0, url.indexOf("#"));
	}
	return url;
    }

}
