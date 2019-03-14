package com.apollo.demo.spring.boot;

import com.apollo.demo.spring.boot.properties.App;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@Slf4j
public class ApolloSpringBootApplication {

    public static final String APP = "app";

    @Value("redis.host")
    private String test;

    @Autowired
    private RefreshScope refreshScope;

    @Autowired
    private App app;

    public static void main(String[] args) {
        SpringApplication.run(ApolloSpringBootApplication.class, args);
    }


    @GetMapping("test")
    public String test() {
        return test;
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

    @ApolloConfigChangeListener
    public void listener(ConfigChangeEvent configChangeEvent) {
        configChangeEvent.changedKeys().forEach(key -> {
            ConfigChange change = configChangeEvent.getChange(key);
            log.info("key [{}] changed [{}] ---> [{}]", key, change.getOldValue(), change.getNewValue());
            // 判断是否是 app 的配置
            if (change.getPropertyName().startsWith("app")) {
                // 刷新 app 这个bean的值
                refreshScope.refresh(APP);
            }
        });
    }

    // 远程配置中心上 app 开头的配置项会进行自动配置
    @ConfigurationProperties(prefix = "app")
    // bean 动态刷新的注释
    @org.springframework.cloud.context.config.annotation.RefreshScope
    @Bean(APP)
    public App app() {
        return new App();
    }
}
