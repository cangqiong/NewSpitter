package com.bigdata.spitter.processor;

import static com.bigdata.spitter.util.PropertiesUtils.getLinkPattern;
import static com.bigdata.spitter.util.HtmlUtils.getALinksByregex;
import static com.bigdata.spitter.util.HtmlUtils.getChildrenElementText;
import static com.bigdata.spitter.util.HtmlUtils.getElementTextBySelector;

import java.util.List;

import com.bigdata.spitter.Page;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * 智联招聘职位详情页面处理类
 * 
 * @author Cang
 *
 */
public class QianchengHtmlHandler implements HtmlHandler {

    @Override
    public Page process(HtmlPage htmlPage) {
	Page page = new Page();
	String html = htmlPage.asXml();
	List<String> links = getALinksByregex(html, getLinkPattern());
	page.setLinks(links);
	String jobname = getElementTextBySelector(html, "div.fixed-inner-box > div.fl > h1");
	if (jobname != null) {
	    String companyName = getElementTextBySelector(html,
		    "div:nth-child(7) > div.fixed-inner-box > div.inner-left.fl > h2 > a");
	    String tags = getElementTextBySelector(html,
		    "div.fixed-inner-box > div.fl > div.welfare-tab-box");
	    String Salary = getChildrenElementText(html, "ul.terminal-ul > li > Strong", 0);
	    String WorkPlace = getChildrenElementText(html, "ul.terminal-ul > li > Strong", 1);
	    String publicDate = getChildrenElementText(html, "ul.terminal-ul > li > Strong", 2);
	    String jobNature = getChildrenElementText(html, "ul.terminal-ul > li > Strong", 3);
	    String workExperience = getChildrenElementText(html, "ul.terminal-ul > li > Strong", 4);
	    String education = getChildrenElementText(html, "ul.terminal-ul > li > Strong", 5);
	    String hireNum = getChildrenElementText(html, "ul.terminal-ul > li > Strong", 6);
	    String jobCategory = getChildrenElementText(html, "ul.terminal-ul > li > Strong", 7);

	    page.putField("jobname", jobname.trim() + ",");
	    page.putField("companyName", companyName + ",");
	    page.putField("tags", tags + ",");
	    page.putField("Salary", Salary + ",");
	    page.putField("WorkPlace", WorkPlace + ",");
	    page.putField("publicDate", publicDate + ",");
	    page.putField("jobNature", jobNature + ",");
	    page.putField("workExperience", workExperience + ",");
	    page.putField("education", education + ",");
	    page.putField("hireNum", hireNum + ",");
	    page.putField("jobCategory", jobCategory);
	}
	return page;
    }

}
