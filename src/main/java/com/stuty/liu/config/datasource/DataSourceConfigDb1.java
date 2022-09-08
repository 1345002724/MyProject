package com.stuty.liu.config.datasource;


import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@MapperScan(basePackages = "com.stuty.liu.mapper.db1", sqlSessionFactoryRef = "db1SqlSessionFactory")
public class DataSourceConfigDb1 {
    // 将这个对象放入Spring容器中
    @Bean(name = "db1DataSource")
    // 表示这个数据源是默认数据源

    // 读取application.properties中的配置参数映射成为一个对象
    // prefix表示参数的前缀
    @ConfigurationProperties(prefix = "spring.datasource.db1")
    public DataSource getDateSource1() {
        return DataSourceBuilder.create().build();
    }
    @Bean(name = "db1SqlSessionFactory")
    // 表示这个数据源是默认数据源

    // @Qualifier表示查找Spring容器中名字为test1DataSource的对象
    public SqlSessionFactory db1SqlSessionFactory(@Qualifier("db1DataSource") DataSource datasource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(datasource);
        bean.setMapperLocations(
                // 设置mybatis的xml所在位置
                new PathMatchingResourcePatternResolver().getResources("classpath*:mapping/db1/*.xml"));
        bean.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
        Interceptor interceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("helperDialect", "mysql");//Oracle数据库时设置为oracle
        properties.setProperty("reasonable", "true");
        interceptor.setProperties(properties);
        bean.setPlugins(new Interceptor[] {interceptor});
        return bean.getObject();
    }
    @Bean("db1SqlSessionTemplate")
    // 表示这个数据源是默认数据源

    public SqlSessionTemplate db1sqlsessiontemplate(
            @Qualifier("db1SqlSessionFactory") SqlSessionFactory sessionfactory) {
        return new SqlSessionTemplate(sessionfactory);
    }

}
