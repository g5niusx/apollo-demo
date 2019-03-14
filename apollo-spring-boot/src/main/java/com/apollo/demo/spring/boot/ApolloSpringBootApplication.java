package com.apollo.demo.spring.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ApolloSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApolloSpringBootApplication.class, args);
    }


    @GetMapping("test")
    public String test() {

        return "test";
    }
}
