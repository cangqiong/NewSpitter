# NewSpitter
基于WebClient实现的爬虫，可以抓取JS动态生成的内容

### 说明文档：

### 新版说明
	因为littleSpitter抓取页面时，只能抓取静态页面，而不能抓取动态生成的内容，如：ajax.因此新版的爬虫采用htmlunit，htmlunit是Java版web浏览器，它能模拟浏览器请求，获得动态生成的内容。
### 技术选型
信息的抓取：	httpunit-2.18.jar
信息的抽取：	jsoup-1.8.3.jar
JSON的处理：	fastjson-1.2.6.jar
日志：	    logback-classic-1.1.3.jar

### 各包说明：
1. com.bigdata.spitter.main		程序入口，程序运行入口
2. com.bigdata.spitter				一些基础类与页面的抓取
3. com.bigdata.spitter.processor	负责页面的抓取与解析
4. com.bigdata.spitter.scheduler   负责管理待抓取的URL，防止重复抓取
5. com.bigdata.spitter.storage	            解析信息的存储

### 配置：配置是通过resource文件夹下的config.properties文件配置，具体含义看其注解。logback.xml为日志配置

### 抓取信息：智联招聘的职位信息，例子：http://jobs.zhaopin.com/136251293251822.htm
具体信息:  
	- 职位名称  
	- 职位标签  
	- 职位薪水  
	- 职位月薪  
	- 工作地点  
	- 发布日期  
	- 工作性质  
	- 工作经验  
	- 最低学历  
	- 招聘人数  
	- 职位类别  
    - 职位描述  
    - 工作地址   
    - 公司：公司名称、公司规模、公司性质、公司行业：互联网/电子商务公司、主页、公司地址、公司介绍
            
### 进度（持续更新）：暂时完成链接的抽取，下一步进行实时抓取

### PS
data.txt已经抓取好的数据
