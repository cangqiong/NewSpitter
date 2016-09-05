package com.bigdata.spitter.processor;

import static com.bigdata.spitter.util.PropertiesUtils.getLinkPattern;

import java.util.List;

import com.bigdata.spitter.Page;
import com.bigdata.spitter.util.HtmlUtils;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class SimpleHtmlHandler implements HtmlHandler {

    @Override
    public Page process(HtmlPage htmlPage) {
	Page page = new Page();
	String html = htmlPage.asXml();
	List<String> links = HtmlUtils.getALinksByregex(html, getLinkPattern());
	page.setLinks(links);
	String jobname = HtmlUtils.getElementTextBySelector(html,
		"div.fixed-inner-box > div.fl > h1");
	if (jobname != null) {
	    String tags = HtmlUtils.getElementTextBySelector(html,
		    "div.fixed-inner-box > div.fl > div.welfare-tab-box");
	    String jobInfo = HtmlUtils.getElementTextBySelector(html, "ul.terminal-ul");

	    page.putField("jobname", jobname);
	    page.putField("tags", tags);
	    page.putField("jobInfo", jobInfo);
	}
	return page;
    }

}
