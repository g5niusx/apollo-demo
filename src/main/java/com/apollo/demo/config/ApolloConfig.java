package com.apollo.demo.config;

import lombok.Data;

/*@Configuration
@EnableApolloConfig*/
public class ApolloConfig {


    @Data
    public static class TestApp {
        private String host;
    }
}
