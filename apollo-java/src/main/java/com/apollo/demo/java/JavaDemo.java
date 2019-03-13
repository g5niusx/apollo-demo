package com.apollo.demo.java;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JavaDemo {
    public static void main(String[] args) {
        Config appConfig = ConfigService.getAppConfig();
        String property  = appConfig.getProperty("timeout", "");
        log.info("读取到配置:{}", property);
    }
}
