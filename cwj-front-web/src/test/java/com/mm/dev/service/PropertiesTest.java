package com.mm.dev.service;

import java.io.IOException;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;

import com.mm.dev.config.ConfigProperties;

public class PropertiesTest extends BaseTest{
	
	@Autowired
    private ConfigProperties configProperties;
	
//	@Autowired
//	private DataSourceConfigProperties dataSourceConfigProperties;
	
	@Value("${spring.datasource.password}")
	private String sqlPassWord;
	
    @Test
    public void test() throws IOException, MyException {
//    	System.out.println("sqlPassWord="+sqlPassWord);
//    	System.out.println("dataSourceConfigProperties="+JSONUtil.toJsonPrettyStr(dataSourceConfigProperties));
//    	System.out.println("configProperties="+JSONUtil.toJsonPrettyStr(configProperties));
    	
    	ClassPathResource cpr = new ClassPathResource("client.conf");  
        ClientGlobal.init(cpr.getClassLoader().getResource("client.conf").getPath());  
		TrackerClient trackerClient = new TrackerClient();
		TrackerServer connection = trackerClient.getConnection();
		System.out.println(connection);
    }
}
