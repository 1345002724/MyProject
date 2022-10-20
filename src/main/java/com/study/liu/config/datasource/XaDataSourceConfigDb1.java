package com.study.liu.config.datasource;


import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.github.pagehelper.PageInterceptor;
import com.mysql.cj.jdbc.MysqlXADataSource;
import oracle.jdbc.xa.client.OracleXADataSource;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
@MapperScan(basePackages = "com.study.liu.mapper.db1", sqlSessionFactoryRef = "db1SqlSessionFactory")
public class XaDataSourceConfigDb1 {
    // 将这个对象放入Spring容器中
    @Bean(name = "db1DataSource")
    // 表示这个数据源是默认数据源

    // 读取application.properties中的配置参数映射成为一个对象
    // prefix表示参数的前缀
    //@ConfigurationProperties(prefix = "spring.datasource.business")
    public DataSource getDateSource1(XaConfigDb1 xaConfigDb1) throws SQLException {

        //cj连接池
//        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
//        mysqlXaDataSource.setURL(xaConfigDb1.getUrl());
//        mysqlXaDataSource.setPassword(xaConfigDb1.getPassword());
//        mysqlXaDataSource.setUser(xaConfigDb1.getUsername());


        //druid连接池
        DruidXADataSource druidXADataSource = new DruidXADataSource();
        druidXADataSource.setUrl(xaConfigDb1.getUrl());
        druidXADataSource.setUsername(xaConfigDb1.getUsername());
        druidXADataSource.setPassword(xaConfigDb1.getPassword());

        //注册到全局事务
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        //xaDataSource.setXaDataSource(mysqlXaDataSource); //cj连接池

        xaDataSource.setXaDataSource(druidXADataSource); //druid连接池

        xaDataSource.setUniqueResourceName("getDateSource1");

        xaDataSource.setMinPoolSize(xaConfigDb1.getMinPoolSize());
        xaDataSource.setMaxPoolSize(xaConfigDb1.getMaxPoolSize());
        xaDataSource.setMaxLifetime(xaConfigDb1.getMaxLifetime());
        xaDataSource.setBorrowConnectionTimeout(xaConfigDb1.getBorrowConnectionTimeout());
        xaDataSource.setLoginTimeout(xaConfigDb1.getLoginTimeout());
        xaDataSource.setMaintenanceInterval(xaConfigDb1.getMaintenanceInterval());
        xaDataSource.setMaxIdleTime(xaConfigDb1.getMaxIdleTime());
        xaDataSource.setTestQuery(xaConfigDb1.getTestQuery());
        return xaDataSource;

    }
    @Bean(name = "db1SqlSessionFactory")
    // 表示这个数据源是默认数据源

    // @Qualifier表示查找Spring容器中名字为test1DataSource的对象
    @Primary
    public SqlSessionFactory db1SqlSessionFactory(@Qualifier("db1DataSource") DataSource datasource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(datasource);
        bean.setMapperLocations(
                // 设置mybatis的xml所在位置
                new PathMatchingResourcePatternResolver().getResources("classpath*:mapping/db1/*.xml"));
        bean.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
        //bean.setDatabaseIdProvider(databaseIdProvider());
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
    @Primary
    public SqlSessionTemplate db1sqlsessiontemplate(
            @Qualifier("db1SqlSessionFactory") SqlSessionFactory sessionfactory) {
        return new SqlSessionTemplate(sessionfactory);
    }
    /******配置事务管理********/

//    @Bean
//    public PlatformTransactionManager bfTransactionManager(@Qualifier("db1DataSource")DataSource prodDataSource) {
//        return new DataSourceTransactionManager(prodDataSource);
//    }
//    @Bean
//    public DatabaseIdProvider databaseIdProvider(){
//        DatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();
//        Properties p = new Properties();
//        p.setProperty("Oracle", "oracle");
//        p.setProperty("MySQL", "mysql");
//        p.setProperty("SQL Server", "sqlserver");
//        databaseIdProvider.setProperties(p);
//        return databaseIdProvider;
//    }

/****配置jdbcTemplate*****/

    @Bean
    public JdbcTemplate Db1JdbcTemplate(@Qualifier("db1DataSource") DataSource db1DataSource) {
        return new JdbcTemplate(db1DataSource);
    }
}
