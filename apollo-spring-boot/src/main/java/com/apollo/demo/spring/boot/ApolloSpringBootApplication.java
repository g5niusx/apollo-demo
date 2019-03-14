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
import org.springframework.cloud.context.scope.refresh.RefreshScope;
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
    public String app() {
        return app.toString();
    }

    @ApolloConfigChangeListener
    public void listener(ConfigChangeEvent configChangeEvent) {
        configChangeEvent.changedKeys().forEach(key -> {
            ConfigChange change = configChangeEvent.getChange(key);
            log.info("key [{}] changed [{}] ---> [{}]", key, change.getOldValue(), change.getNewValue());
            if (change.getPropertyName().startsWith("app")) {
                // 刷新 app 这个bean的值
                refreshScope.refresh(APP);
            }
        });
    }
}
