package com.bigdata.spitter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * 抓取的页面对象
 * 
 * @author Cang
 *
 */
@Setter
@Getter
public class Page {

    // 页面的链接
    private String url;
    // 要抓取的页面信息
    private Map<String, Object> fields = new LinkedHashMap<String, Object>();
    // 页面的链接
    private List<String> links;

    public void putField(String key, String value) {
	fields.put(key, value);
    }

}
