package com.zl.multiple.datasource.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zl.multiple.datasource.entity.MultiDatasourceProperties;
import com.zl.multiple.datasource.util.DynamicDataSource;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: springboot-multiple-dataSources
 * @description: 自动配置多数据源问题的配置类
 * @author: 张乐
 * @create: 2019-01-11 17:33
 **/
@Configuration
@AutoConfigureOrder(value = Ordered.HIGHEST_PRECEDENCE)
@EnableConfigurationProperties(MultiDatasourceProperties.class)
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
@ComponentScan("com.zl.multiple.datasource")
public class MultiDatasourceConfiguration {

    public static final int INITIAL_CAPACITY = 10;

    @Bean("dynamicDataSource")
    public DynamicDataSource dynamicDataSource(MultiDatasourceProperties multiDatasourceProperties, DataSourceProperties defaultDataSourceProperties) throws ClassNotFoundException {

        //创建动态数据源
        DynamicDataSource dynamicDataSource = new DynamicDataSource();

        Map<Object, Object> dataSources = new HashMap(INITIAL_CAPACITY);

        Map<String, DataSourceProperties> properties = multiDatasourceProperties.getMulti();
        String poolClassName = multiDatasourceProperties.getPoolClassName();
        if (poolClassName == null) {
            //默认使用c3p0数据库连接池
            poolClassName = ComboPooledDataSource.class.getName();
        }
        Class<DataSource> poolClass = (Class<DataSource>) Class.forName(poolClassName);

        if (defaultDataSourceProperties != null) {
            DataSource defaultDb = getDataSource(poolClass, defaultDataSourceProperties);
            dynamicDataSource.setDefaultTargetDataSource(defaultDb);
        }

        for (Map.Entry<String, DataSourceProperties> entry : properties.entrySet()) {
            DataSource db = getDataSource(poolClass, entry.getValue());
            dataSources.put(entry.getKey(), db);
        }
        dynamicDataSource.setTargetDataSources(dataSources);

        return dynamicDataSource;
    }

    private DataSource getDataSource(Class<DataSource> defaultType, DataSourceProperties properties) {
        Class<? extends DataSource> type = properties.getType();
        return properties.initializeDataSourceBuilder().type(type == null ? defaultType : type).build();
    }
}
