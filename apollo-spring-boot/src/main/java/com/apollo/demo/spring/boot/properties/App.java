package com.apollo.demo.spring.boot.properties;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 通过远程直接注入bean对象
 */
@Data
@Component
public class App {

    private String name;
    private String host;
    private String port;
}
