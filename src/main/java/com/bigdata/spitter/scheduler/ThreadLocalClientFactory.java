package com.bigdata.spitter.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;

/**
 * HTMLUnit的WebClient本地实例存储
 * 
 * @author Cang
 *
 */
public class ThreadLocalClientFactory {

    private static Logger logger = LoggerFactory.getLogger(ThreadLocalClientFactory.class);
    // 单例工厂模式
    private final static ThreadLocalClientFactory instance = new ThreadLocalClientFactory();
    // 线程的本地实例存储器，用于存储WebClient实例
    private ThreadLocal<WebClient> clientThreadLocal;

    public ThreadLocalClientFactory() {
	clientThreadLocal = new ThreadLocal<WebClient>();
    }

    /**
     * 获取工厂实例
     * 
     * @return 工厂实例
     */
    public static ThreadLocalClientFactory getInstance() {
	return instance;
    }

    /**
     * 获取WebClient实例
     * 
     * @return WebClient
     */
    public WebClient getClient() {
	WebClient client = null;

	/**
	 * 如果当前线程已有WebClient实例，则直接返回该实例 否则重新创建一个WebClient实例并存储于当前线程的本地变量存储器
	 */
	if ((client = clientThreadLocal.get()) == null) {
	    client = new WebClient(BrowserVersion.CHROME);
	    client.getOptions().setUseInsecureSSL(true);
	    // 1、启用js
	    client.getOptions().setJavaScriptEnabled(true);
	    // 2 、禁用css
	    client.getOptions().setCssEnabled(false);
	    // 3 、启动客户端重定向
	    client.getOptions().setRedirectEnabled(true);

	    // 4、js出错，是否抛出异常
	    client.getOptions().setThrowExceptionOnScriptError(false);
	    client.getOptions().setThrowExceptionOnFailingStatusCode(false);

	    // 5、设置超时
	    client.getOptions().setTimeout(50000);
	    // 设置js
	    // client.setAjaxController(new
	    // NicelyResynchronizingAjaxController());
	    // 6、等待js渲染后获取最终页面
	    // client.waitForBackgroundJavaScriptStartingBefore(10000);
	    // 设置js超时时间
	    client.setJavaScriptTimeout(5000);

	    clientThreadLocal.set(client);

	    logger.info("为线程 [ " + Thread.currentThread().getName() + " ] 创建新的WebClient实例!");
	}
	return client;
    }
}
