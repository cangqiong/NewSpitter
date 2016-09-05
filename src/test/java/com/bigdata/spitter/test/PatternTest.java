package com.bigdata.spitter.test;

import java.io.IOException;
import java.util.regex.Pattern;

import org.apache.http.client.ClientProtocolException;

import com.bigdata.spitter.util.PropertiesUtils;

/**
 * Hello world!
 *
 */
public class PatternTest 
{
    public static void main( String[] args ) throws ClientProtocolException, IOException
    {
    	String url = "http://jobs.zhaopin.com/275149789250083.htm?ff=100&ss=301";
    	Pattern p = PropertiesUtils.getLinkPattern();
    	System.out.println(p);
    	System.out.println(p.matcher(url).find());
    }
}
