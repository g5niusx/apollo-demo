package com.apollo.demo.spring.boot.properties;

import lombok.Data;

/**
 * 远程数据源配置
 */
@Data
public class DataSourceProperties {

    private String url;
    private String username;
    private String password;

}
