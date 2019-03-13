package com.apollo.demo.spring;

import com.apollo.demo.spring.config.ApolloConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Autowired
    private ApolloConfig.TestApp testApp;

    @GetMapping("test")
    public String test() {
        return "value from apollo :" + testApp.getHost();
    }
}
