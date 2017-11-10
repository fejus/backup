package com.adatafun.airportshop.order.conf;

import com.adatafun.airportshop.order.conf.util.ConfigCenterUtils;
import com.adatafun.utils.common.StringUtils;
import com.alibaba.druid.pool.DruidDataSource;
import com.mysql.jdbc.Driver;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.*;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Map;
import java.util.Properties;

/**
 * Created by zhengzh on 2016/8/17.
 */
@Configuration
@EnableTransactionManagement
public class MyBatisConfig {

    @Bean(name = "resourceLoader")
    public ResourceLoader resourceLoader(){
        return new DefaultResourceLoader();
    }

    @Bean(name = "resourcePatternResolver")
    public ResourcePatternResolver resourcePatternResolver(){
        return new PathMatchingResourcePatternResolver();
    }

    @Profile("production")
    @Bean(name = "dataSource",initMethod = "init",destroyMethod = "close")
    public DruidDataSource dataSource() throws Exception{
        DruidDataSource druidDataSource = new DruidDataSource();
        Map<String, String> properties = ConfigCenterUtils.getAppProperties().getProperties("data.source");
        druidDataSource.setName(properties.get("mysql.conn.name"));
        druidDataSource.setUsername(properties.get("mysql.username"));
        druidDataSource.setPassword(properties.get("mysql.password"));
        druidDataSource.setUrl(properties.get("mysql.url"));
        druidSettings(druidDataSource);
        return druidDataSource;
    }

    @Bean(name = "transactionManager")
    public DataSourceTransactionManager dataSourceTransactionManager(DruidDataSource dataSource){
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.adatafun.airportshop.order.mapper");
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        return mapperScannerConfigurer;
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactoryBean sqlSessionFactoryBean(DruidDataSource dataSource,ResourceLoader resourceLoader,ResourcePatternResolver resourcePatternResolver) throws Exception{
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(resourcePatternResolver.getResources("classpath:conf/mybatis/mapper/*.xml"));
//        sqlSessionFactoryBean.setConfigLocation(resourceLoader.getResource("classpath:mybatis.xml"));
        sqlSessionFactoryBean.setTypeAliasesPackage("com.adatafun.airportshop.order.pojo.po");
        /**
         * 此处相当于mybatis.xml的配置
         */
        Properties mybatisProp = new Properties();
        mybatisProp.setProperty("logImpl", "LOG4J");
        mybatisProp.setProperty("cacheEnabled","true");
        mybatisProp.setProperty("useGeneratedKeys","true");
        sqlSessionFactoryBean.setConfigurationProperties(mybatisProp);
        return sqlSessionFactoryBean;
    }

    public void druidSettings(DruidDataSource druidDataSource) throws Exception{
        Map<String, String> properties = ConfigCenterUtils.getAppProperties().getProperties("data.source");
        druidDataSource.setMaxActive(StringUtils.getInteger(properties.get("max.active"), 40));
        druidDataSource.setInitialSize(StringUtils.getInteger(properties.get("initial.size"), 3));
        druidDataSource.setUseUnfairLock(StringUtils.getBoolean(properties.get("use.unfair.lock"), Boolean.TRUE));
        druidDataSource.setMinIdle(StringUtils.getInteger(properties.get("min.idle"), 10));
        druidDataSource.setMaxWait(StringUtils.getInteger(properties.get("max.wait"), 60000));
        druidDataSource.setPoolPreparedStatements(StringUtils.getBoolean(properties.get("set.pool.prepared.statements"), Boolean.FALSE));
        druidDataSource.setTestOnBorrow(StringUtils.getBoolean(properties.get("test.on.borrow"), Boolean.FALSE));
        druidDataSource.setTestOnReturn(StringUtils.getBoolean(properties.get("test.on.return"), Boolean.FALSE));
        druidDataSource.setTestWhileIdle(StringUtils.getBoolean(properties.get("test.while.idle"), Boolean.TRUE));
        druidDataSource.setTimeBetweenEvictionRunsMillis(StringUtils.getLong(properties.get("time.between.eviction.runs.millis"), 6000L));
        druidDataSource.setMinEvictableIdleTimeMillis(StringUtils.getInteger(properties.get("min.evictable.idle.time.millis"), 30000 * 4));
        druidDataSource.setRemoveAbandoned(StringUtils.getBoolean(properties.get("remove.abandoned"), Boolean.TRUE));
        druidDataSource.setRemoveAbandonedTimeout(StringUtils.getInteger(properties.get("remove.abandoned.timeout"), 180));
        druidDataSource.setLogAbandoned(StringUtils.getBoolean(properties.get("log.abandoned"), Boolean.TRUE));
        druidDataSource.setFilters(StringUtils.getString(properties.get("filters"), "stat"));
        druidDataSource.setTimeBetweenLogStatsMillis(StringUtils.getInteger(properties.get("time.between.log.stats.millis"), 60000 * 60)); //每10分钟自动将监控的数据存储到日志中
        druidDataSource.setDriver(new Driver());
    }
}
