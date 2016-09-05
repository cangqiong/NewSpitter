package com.bigdata.spitter.scheduler;

import java.util.List;

import com.bigdata.spitter.Request;

/**
 * 请求队列接口
 * 
 * @author Cang
 *
 */
public interface RequestQueue {

    public boolean addRequest(String url);

    public void addRequest(List<String> links);

    public Request getRequest();

}
