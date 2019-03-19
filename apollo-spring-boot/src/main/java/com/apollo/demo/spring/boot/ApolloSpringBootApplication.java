package com.apollo.demo.spring.boot;

import com.apollo.demo.spring.boot.properties.App;
import com.apollo.demo.spring.boot.properties.DataSourceProperties;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@RestController
@Slf4j
public class ApolloSpringBootApplication implements InitializingBean {

    public static final  String APP                    = "app";
    private static final String DATA_SOURCE_PROPERTIES = "dataSourceProperties";
    private static final String DATA_SOURCE            = "dataSource";
    private static final String SQL                    = "select * from Namespace;";
    @Value("redis.host")
    private              String test;

    @Autowired
    private RefreshScope refreshScope;

    @Autowired
    private App app;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private HikariDataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(ApolloSpringBootApplication.class, args);
    }


    private void run() {
        ResultSet execute = jdbcTemplate.execute(SQL, (PreparedStatementCallback<ResultSet>) PreparedStatement::executeQuery);
        log.info("查询结果为:{}", execute == null ? "结果为空" : execute.toString());
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
    public String appInfo() {
        return app.toString();
    }

    // 监听 application 和 TEST1.datasource 的命名空间配置
    @ApolloConfigChangeListener(value = {"application", "TEST1.datasource"})
    public void listener(ConfigChangeEvent configChangeEvent) {
        configChangeEvent.changedKeys().forEach(key -> {
            ConfigChange change = configChangeEvent.getChange(key);
            log.info("key [{}] changed [{}] ---> [{}]", key, change.getOldValue(), change.getNewValue());
            // 判断是否是 app 的配置
            if (change.getPropertyName().startsWith("app")) {
                // 刷新 app 这个bean的值
                refreshScope.refresh(APP);
            }
            // 如果是datasource的命名空间，并且是数据源的配置。去更新数据源配置
            if (configChangeEvent.getNamespace().equalsIgnoreCase("TEST1.datasource") && change.getPropertyName().startsWith("datasource")) {
                // 更新properties的配置
                refreshScope.refresh(DATA_SOURCE_PROPERTIES);
                // 更新数据源的配置
                refreshScope.refresh(DATA_SOURCE);
            }
        });
    }

    // 远程配置中心上 app 开头的配置项会进行自动配置
    @ConfigurationProperties(prefix = "app")
    // bean 动态刷新的注释
    @org.springframework.cloud.context.config.annotation.RefreshScope
    @Bean(APP)
    public App app() {
        return new App();
    }

    /**
     * 获取远程的数据源配置
     *
     * @return
     */
    @ConfigurationProperties(prefix = "datasource")
    @org.springframework.cloud.context.config.annotation.RefreshScope
    @Bean(DATA_SOURCE_PROPERTIES)
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * 将远程的数据源配置注入到 datasource 中
     *
     * @return
     */
    @Bean(DATA_SOURCE)
    public HikariDataSource dataSource(DataSourceProperties dataSourceProperties) {
        return DataSourceBuilder.create().driverClassName("com.mysql.cj.jdbc.Driver")
                .password(dataSourceProperties.getPassword())
                .username(dataSourceProperties.getUsername())
                .url(dataSourceProperties.getUrl())
                .type(HikariDataSource.class).build();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 通过定时执行sql来测试动态替换数据源会不会对当前数据源产生影响和报错
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(20);
        scheduledThreadPoolExecutor.scheduleAtFixedRate(this::run, 0, 1, TimeUnit.SECONDS);
    }
}
