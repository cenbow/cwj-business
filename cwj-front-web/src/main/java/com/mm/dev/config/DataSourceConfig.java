package com.mm.dev.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;

import com.mm.dev.expands.mybatis.plugins.PaginationResultSetInterceptor;
import com.mm.dev.expands.mybatis.plugins.PaginationStatementInterceptor;

/**
 * @ClassName: DataSourceConfig 
 * @Description: 集成mybatis配置
 * @author zhangxy
 * @date 2017年11月3日 上午9:32:35
 */
@Configuration
@MapperScan(basePackages = "com.mm.dev.dao.mapper", sqlSessionFactoryRef = "sqlSessionFactory", sqlSessionTemplateRef = "sqlSessionTemplate")
@EnableTransactionManagement
public class DataSourceConfig {
	
	@Autowired
    DataSource dataSource;

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        // 获取properties中的对应配置信息
        String mapperPackage = "/mapper/*/*Mapper.xml";
        String dialect = "mysql";
        Properties properties = new Properties();
        properties.setProperty("dialect", dialect);
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setConfigurationProperties(properties);
        // 设置MapperLocations路径
        if(!StringUtils.isEmpty(mapperPackage)){
            ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
            sessionFactory.setMapperLocations(resourcePatternResolver.getResources(mapperPackage));
        }
        // 设置插件
        sessionFactory.setPlugins(new Interceptor[]{
                new PaginationStatementInterceptor(),
                new PaginationResultSetInterceptor()
        });

        return sessionFactory.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
