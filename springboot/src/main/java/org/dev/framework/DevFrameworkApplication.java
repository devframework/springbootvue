package org.dev.framework;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication

@MapperScan("org.dev.framework.modules.**.mapper.**")
@EnableAspectJAutoProxy(proxyTargetClass=true)
@EnableAutoConfiguration
@ComponentScan
public class DevFrameworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevFrameworkApplication.class, args);
    }

}
