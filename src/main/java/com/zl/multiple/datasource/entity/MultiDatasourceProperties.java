package com.zl.multiple.datasource.entity;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: test
 * @description: 多数据源配置实体类
 * @author: 张乐
 * @create: 2019-03-21 17:58
 **/
@ConfigurationProperties(prefix = "spring.datasource")
public class MultiDatasourceProperties {

    private String poolClassName;

    private Map<String, DataSourceProperties> multi = new HashMap<String, DataSourceProperties>();

    public Map<String, DataSourceProperties> getMulti() {
        return multi;
    }

    public void setMulti(Map<String, DataSourceProperties> multi) {
        this.multi = multi;
    }

    public String getPoolClassName() {
        return poolClassName;
    }

    public void setPoolClassName(String poolClassName) {
        this.poolClassName = poolClassName;
    }
}
