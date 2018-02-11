package com.mm.dev;
import java.nio.charset.Charset;

import javax.servlet.MultipartConfigElement;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.HttpMessageConverter;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

/**
* @ClassName: DevApplication 
* @Description:应用启动类
* @author Jacky
* @date 2017年7月29日 下午12:16:43
 */
@SpringBootApplication
@EnableJpaRepositories
@EnableEncryptableProperties
@MapperScan(basePackages = "com.mm.dev.dao.mapper", sqlSessionFactoryRef = "sqlSessionFactory", sqlSessionTemplateRef = "sqlSessionTemplate")
public class DevApplication {
	private static final Logger logger = LoggerFactory.getLogger(DevApplication.class);
	
    public static void main(String[] args) {
    	long currentTimeMillis = System.currentTimeMillis();
        SpringApplication.run(DevApplication.class, args);
        long currentTimeMillis2 = System.currentTimeMillis();
        logger.info("应用启动成功耗时:{}秒",currentTimeMillis2-currentTimeMillis);
    }
    
    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
       FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
       FastJsonConfig fastJsonConfig = new FastJsonConfig();
       fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
       fastJsonConfig.setCharset(Charset.forName("UTF-8"));
       fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
       fastConverter.setFastJsonConfig(fastJsonConfig);
       HttpMessageConverter<?> converter = fastConverter;
       return new HttpMessageConverters(converter);
    }
    
    /**  
     * 文件上传配置  
     * @return  
     */  
    @Bean  
    public MultipartConfigElement multipartConfigElement() {  
        MultipartConfigFactory factory = new MultipartConfigFactory();  
        //文件最大  
        factory.setMaxFileSize("102400KB"); //KB,MB  
        //设置总上传数据总大小  
        factory.setMaxRequestSize("102400KB");  
        return factory.createMultipartConfig();  
    }  
    
//    @Bean(name = "stringEncryptor")
//    public StringEncryptor stringEncryptor() {
//        return new DefaultEncryptor();
//    }

}
