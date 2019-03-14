package com.apollo.demo.spring;

import com.apollo.demo.spring.config.ApolloConfig;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChange;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class DemoController {

    @Autowired
    private ApolloConfig.TestApp testApp;


    @GetMapping("test")
    public String test() {
        return "value from apollo :" + testApp.getHost();
    }

    @Bean
    public Config configService() {
        Config appConfig = ConfigService.getAppConfig();
        appConfig.addChangeListener(changeEvent -> {
            changeEvent.changedKeys().forEach(key -> {
                ConfigChange change = changeEvent.getChange(key);
                log.info("key [{}] changed [{}] ---> [{}]", key, change.getOldValue(), change.getNewValue());
            });
        });
        return appConfig;
    }

}
