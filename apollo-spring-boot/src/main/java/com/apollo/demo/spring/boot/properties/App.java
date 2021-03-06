package com.apollo.demo.spring.boot.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import static com.apollo.demo.spring.boot.ApolloSpringBootApplication.APP;

/**
 * 通过远程直接注入bean对象
 */
@Data
public class App {

    private String name;
    private String host;
    private String port;
}
