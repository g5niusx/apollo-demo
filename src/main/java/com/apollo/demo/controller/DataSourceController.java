package com.apollo.demo.controller;

import com.apollo.demo.config.ApolloConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class DataSourceController {

    @Autowired
    private ApolloConfig.TestApp testApp;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        return testApp.getHost();
    }
}
