package com.bigdata.spitter.db;

import java.util.List;

import lombok.Getter;

import com.bigdata.spitter.Request;
import com.bigdata.spitter.scheduler.LinkFilter;
import com.bigdata.spitter.scheduler.RequestQueue;

/**
 * 使用BacklyDB实现请求队列
 * 
 * @author Cang
 *
 */
@Getter
public class BDBQueue implements RequestQueue, LinkFilter {

    private BDBase<String, Request> pendingUrisDB = null;
    private BDBase<String, Request> visitedUrisDB = null;
    private String homeDictory = "/tmp/berkeleydata";

    public BDBQueue() {
//	pendingUrisDB = new BDBase<String, Request>(homeDictory, "pendingUrisDB");
	pendingUrisDB = new BDBase<String, Request>("pendingUrisDB");
	pendingUrisDB.binding(String.class, Request.class);
//	visitedUrisDB = new BDBase<String, Request>(homeDictory, "visitedUrisDB");
	visitedUrisDB = new BDBase<String, Request>("visitedUrisDB");
	visitedUrisDB.binding(String.class, Request.class);
    }

    @Override
    public boolean accept(String url) {
	if (!pendingUrisDB.containsKey(url) && !visitedUrisDB.containsKey(url)) {
	    return true;
	}
	return false;
    }

    @Override
    public boolean addRequest(String url) {
	if (accept(url)) {
	    return pendingUrisDB.put(url, Request.warpper(url));
	}
	return false;
    }

    @Override
    public void addRequest(List<String> links) {
	for (String link : links) {
	    addRequest(link);
	}
    }

    @Override
    public Request getRequest() {
	if (!pendingUrisDB.isEmpty()) {
	    Request req = pendingUrisDB.getNext();
	    visitedUrisDB.put(req.getUrl(), req);
	    return req;
	}
	return null;
    }

    public static void main(String[] args) {
	BDBQueue queue = new BDBQueue();
	queue.addRequest("http://www.43343.com");
	queue.getRequest();
    }
}