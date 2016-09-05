package com.bigdata.spitter.test;

import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class EffectLinkTest {
	public static void main(String[] args) throws MalformedURLException {
		URL url = new URL("http://127.0.0.1/Java/base/949046_2.html?tet=3#dst");
		String testUrl = "<a href='http://127.0.0.1/Java/base/949046_2.html?tet=3#dst'>dff</a>";
		Document doc = Jsoup.parse(testUrl);
		System.out.println(doc.absUrl("href"));
	}
}