package com.common.gecco;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.Html;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.RequestParameter;
import com.geccocrawler.gecco.annotation.Text;
import com.geccocrawler.gecco.spider.HtmlBean;
import com.geccocrawler.gecco.spider.SpiderBean;

@Gecco(matchUrl="https://baike.baidu.com/item/{project}/85979", pipelines="consolePipeline")
public class BaiduBaike implements SpiderBean {

    private static final long serialVersionUID = -7127412585200687225L;
    
    @RequestParameter("project")
    private String project;//url中的{project}值
    
    @HtmlField(cssPath="head title")
    private String title;//抽取页面中的title
    
    public static void main(String[] args) {
        GeccoEngine.create()
        //工程的包路径
        .classpath("com.common.gecco")
        //开始抓取的页面地址
        .start("https://baike.baidu.com/item/java/85979")
        //开启几个爬虫线程
        .thread(1)
        //单个爬虫每次抓取完一个请求后的间隔时间
        .interval(2000)
        //循环抓取
        .loop(false)
        //使用pc端userAgent
        .mobile(false)
        //非阻塞方式运行
        .start();
    }



	public String getProject() {
		return project;
	}



	public void setProject(String project) {
		this.project = project;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}
}