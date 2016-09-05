package com.bigdata.spitter.test;

import java.net.MalformedURLException;
import java.net.URL;

import com.bigdata.spitter.robots.Robots;

public class RobotsTest {
    public static void main(String[] args) throws MalformedURLException {
	System.out.println(Robots.isRobotAllowed(new URL("http://www.oschina.net/code/download_")));
	System.out.println(Robots.isRobotAllowed(new URL("http://www.oschina.net/admin/dfdf")));
    }
}