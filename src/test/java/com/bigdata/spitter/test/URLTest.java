package com.bigdata.spitter.test;

import java.net.MalformedURLException;
import java.net.URL;

public class URLTest {  
    public static void main(String[] args) throws MalformedURLException {
	URL url = new URL("http://127.0.0.1/Java/base/949046_2.html?tet=3#df?df=df");
	System.out.println(url.getHost());
	System.out.println(url.getFile());
	System.out.println(url.getPath());
	System.out.println(url.getQuery());
    }
} 