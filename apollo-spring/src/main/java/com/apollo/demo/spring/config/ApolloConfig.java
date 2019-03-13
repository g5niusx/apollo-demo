package com.apollo.demo.spring.config;

import lombok.Data;

public class ApolloConfig {


    @Data
    public static class TestApp {
        private String host;
    }
}
