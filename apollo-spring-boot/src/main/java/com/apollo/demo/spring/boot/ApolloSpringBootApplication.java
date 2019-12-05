package com.apollo.demo.spring.boot;

import com.apollo.demo.spring.boot.properties.App;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@Slf4j
public class ApolloSpringBootApplication implements BeanPostProcessor {

    public static final String APP = "app";
    private static final String DATA_SOURCE_PROPERTIES = "dataSourceProperties";
    private static final String DATA_SOURCE = "dataSource";
    private static final String SQL = "select * from Namespace;";
    @Value("redis.host")
    private String test;


    @Autowired
    private App app;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private HikariDataSource dataSource;

    public static void main(String[] args) {
        System.setProperty("env","dev");
        SpringApplication.run(ApolloSpringBootApplication.class, args);
    }


    @GetMapping("test")
    public String test() {
        log.info("info....");
        log.warn("warn....");
        log.trace("trace....");
        log.error("error....");
        return test;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        log.error("error bean name {}",beanName);
        log.info("info bean name {}",beanName);
        return null;
    }

    /**
     * 测试从远程直接加载bean
     *
     * @return
     */
    @GetMapping("app")
    public String appInfo() {
        return app.toString();
    }


}
