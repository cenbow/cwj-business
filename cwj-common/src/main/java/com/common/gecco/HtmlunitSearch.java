package com.common.gecco;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class HtmlunitSearch {
	
	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		// 得到浏览器对象，直接New一个就能得到，现在就好比说你得到了一个浏览器了  
	    WebClient webclient = new WebClient();  
	  
	    // 这里是配置一下不加载css和javaScript,配置起来很简单，是不是  
	    webclient.getOptions().setCssEnabled(false);  
	    webclient.getOptions().setJavaScriptEnabled(false);  
	  
	    // 做的第一件事，去拿到这个网页，只需要调用getPage这个方法即可  
	    HtmlPage htmlpage = webclient.getPage("http://news.baidu.com/advanced_news.html");  
	  
	    // 根据名字得到一个表单，查看上面这个网页的源代码可以发现表单的名字叫“f”  
	    final HtmlForm form = htmlpage.getFormByName("f");  
	    // 同样道理，获取”百度一下“这个按钮  
	    final HtmlSubmitInput button = form.getInputByValue("百度一下");  
	    // 得到搜索框  
	    final HtmlTextInput textField = form.getInputByName("q1");  
	    // 最近周星驰比较火呀，我这里设置一下在搜索框内填入”周星驰“  
	    textField.setValueAttribute("周星驰");  
	    // 输入好了，我们点一下这个按钮  
	    final HtmlPage nextPage = button.click();  
	    // 我把结果转成String  
	    String result = nextPage.asXml();  
	    System.out.println(result);  
	    writeFile(result);
	}
	
	//写入文档的方法
    public static void writeFile(String inputText){
        try {
            if(new File("D:\\周星驰.html").exists()){
                FileWriter fw1=new FileWriter("D:\\周星驰.html",true);
                PrintWriter pw = new PrintWriter(fw1);
                pw.print(inputText);
                pw.flush();
                pw.close();

            }else{
                File f1 =new File("D:\\周星驰.html");
                FileWriter fw1=new FileWriter("D:\\周星驰.html",true);
                PrintWriter pw = new PrintWriter(fw1,true);
//                pw.println("姓名"+"\t"+"性别"+"\t"+"职业"+"\t"+"国籍"+"\t"+"生日"+"\t"+"星座"+"\t"+"身高");
                pw.print(inputText);
                pw.flush();
                pw.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
      }

}
