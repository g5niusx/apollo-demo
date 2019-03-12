package com.apollo.demo;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Boot {
    public static void main(String[] args) throws InterruptedException {
        Config appConfig = ConfigService.getAppConfig();
        String property  = appConfig.getProperty("timeout", "");
        log.info("读取到配置:{}", property);
    }
}
