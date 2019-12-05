package com.apollo.demo.spring.boot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;

@Slf4j
public class ApolloStartingConfig implements ApplicationListener<ApplicationStartingEvent>, Ordered {
    @Override
    public void onApplicationEvent(ApplicationStartingEvent applicationStartingEvent) {
//        SpringApplication springApplication = (SpringApplication) applicationStartingEvent.getSource();
//        Set<ApplicationListener<?>> listeners = springApplication.getListeners();
//        ArrayList<ApplicationListener<?>> applicationListeners = new ArrayList<>(listeners);
//        int loggingIndex = 0;
//        for (int i = 0; i < applicationListeners.size(); i++) {
//            if (applicationListeners.get(i).getClass().equals(LoggingApplicationListener.class)) {
//                loggingIndex = i;
//            }
//        }
//        applicationListeners.add(loggingIndex, new ApolloConfig());
        log.info("test......");
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
