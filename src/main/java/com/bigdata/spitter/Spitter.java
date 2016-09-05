package com.bigdata.spitter;

import static com.bigdata.spitter.util.PropertiesUtils.getThreadNum;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bigdata.spitter.processor.HtmlHandler;
import com.bigdata.spitter.processor.SimpleHtmlHandler;
import com.bigdata.spitter.storage.ConsoleStorage;
import com.bigdata.spitter.storage.Storage;
import com.bigdata.spitter.util.PropertiesUtils;

/**
 * 爬虫主类
 * 
 * @author Cang
 *
 */
public class Spitter {

	private static Logger logger = LoggerFactory.getLogger(Spitter.class);

	private int threadNum = getThreadNum();
	// 设置页面响应处理类
	private HtmlHandler handler = null;
	private ExecutorService executorService = null;
	// 存储方法
	private Storage storage = null;
	private String startUrl = PropertiesUtils.getStartUrl();

	public Spitter(HtmlHandler handle) {
		setHtmlHandler(handle);
	}

	/**
	 * 爬虫状态初始化
	 */
	private void init() {
		executorService = Executors.newFixedThreadPool(threadNum);
		// 加入种子链接
		addSends(startUrl);
		if (handler == null) {
			// 默认页面处理方法
			setHtmlHandler(new SimpleHtmlHandler());
		}
		if (storage == null) {
			// 默认存储方法
			setStorage(new ConsoleStorage());
		}
	}

	/**
	 * 设置页面处理对象
	 * 
	 * @param handler
	 *            页面处理对象
	 */
	public void setHtmlHandler(HtmlHandler handler) {
		this.handler = handler;
	}

	/**
	 * 设置存储方式
	 * 
	 * @param storage
	 *            存储方式
	 */
	public void setStorage(Storage storage) {
		this.storage = storage;
	}

	/**
	 * 添加种子链接
	 * 
	 * @param startUrl
	 *            起始链接
	 */
	public void addSends(String startUrl) {
		SpitterInstance.taskQueue.addRequest((startUrl));
	}

	/**
	 * 开始运行爬虫
	 */
	public void start() {
		// 初始化爬虫配置
		init();
		logger.info("Spider started!");
		for (int i = 0; i < threadNum; i++) {
			executorService.execute(new SpitterInstance(handler, storage));
		}
		executorService.shutdown();
	}

	public String getStartUrl() {
		return startUrl;
	}

	public void setStartUrl(String startUrl) {
		this.startUrl = startUrl;
	}

}
