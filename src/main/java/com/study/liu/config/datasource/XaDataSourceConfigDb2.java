package com.study.liu.config.datasource;


import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.github.pagehelper.PageInterceptor;
import oracle.jdbc.xa.client.OracleXADataSource;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
@MapperScan(basePackages = "com.study.liu.mapper.db2", sqlSessionFactoryRef = "db2SqlSessionFactory")
public class XaDataSourceConfigDb2 {
    // 将这个对象放入Spring容器中
    @Bean(name = "db2DataSource")
    // 表示这个数据源是默认数据源

    // 读取application.properties中的配置参数映射成为一个对象
    // prefix表示参数的前缀
    public DataSource getDateSource2(XaConfigDb2 XaConfigDb2) throws SQLException {

        //cj连接池
//        OracleXADataSource oracleXaDataSource = new OracleXADataSource();
//        oracleXaDataSource.setURL(XaConfigDb2.getUrl());
//        oracleXaDataSource.setPassword(XaConfigDb2.getPassword());
//        oracleXaDataSource.setUser(XaConfigDb2.getUsername());

        //druid连接池
        DruidXADataSource druidXADataSource = new DruidXADataSource();
        druidXADataSource.setUrl(XaConfigDb2.getUrl());
        druidXADataSource.setUsername(XaConfigDb2.getUsername());
        druidXADataSource.setPassword(XaConfigDb2.getPassword());

        //注册到全局事务
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();

        //xaDataSource.setXaDataSource(oracleXaDataSource); //cj连接池
        xaDataSource.setXaDataSource(druidXADataSource); //druid连接池

        xaDataSource.setUniqueResourceName("getDateSource2");

        xaDataSource.setMinPoolSize(XaConfigDb2.getMinPoolSize());
        xaDataSource.setMaxPoolSize(XaConfigDb2.getMaxPoolSize());
        xaDataSource.setMaxLifetime(XaConfigDb2.getMaxLifetime());
        xaDataSource.setBorrowConnectionTimeout(XaConfigDb2.getBorrowConnectionTimeout());
        xaDataSource.setLoginTimeout(XaConfigDb2.getLoginTimeout());
        xaDataSource.setMaintenanceInterval(XaConfigDb2.getMaintenanceInterval());
        xaDataSource.setMaxIdleTime(XaConfigDb2.getMaxIdleTime());
        xaDataSource.setTestQuery(XaConfigDb2.getTestQuery());
        return xaDataSource;
    }
    @Bean(name = "db2SqlSessionFactory")
    // 表示这个数据源是默认数据源

    // @Qualifier表示查找Spring容器中名字为test1DataSource的对象
    public SqlSessionFactory db3SqlSessionFactory(@Qualifier("db2DataSource") DataSource datasource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(datasource);
        bean.setMapperLocations(
                // 设置mybatis的xml所在位置
                new PathMatchingResourcePatternResolver().getResources("classpath*:mapping/db2/*.xml"));
        bean.setConfigLocation(new ClassPathResource("mybatis-config.xml"));

        //bean.setDatabaseIdProvider(databaseIdProvider());
        Interceptor interceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("helperDialect", "oracle");//Oracle数据库时设置为oracle
        properties.setProperty("reasonable", "true");
        interceptor.setProperties(properties);
        bean.setPlugins(new Interceptor[] {interceptor});

        return bean.getObject();
    }
    @Bean("db2SqlSessionTemplate")
    // 表示这个数据源是默认数据源

    public SqlSessionTemplate db2sqlsessiontemplate(
            @Qualifier("db2SqlSessionFactory") SqlSessionFactory sessionfactory) {
        return new SqlSessionTemplate(sessionfactory);
    }


//    @Bean
//    public DatabaseIdProvider databaseIdProvider(){
//        DatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();
//        Properties p = new Properties();
//        p.setProperty("Oracle", "oracle");
//        databaseIdProvider.setProperties(p);
//        return databaseIdProvider;
//    }


    /****配置jdbcTemplate*****/

//    @Bean
//    public JdbcTemplate Db2JdbcTemplate(@Qualifier("db2DataSource") DataSource db2DataSource) {
//        return new JdbcTemplate(db2DataSource);
//    }

}
