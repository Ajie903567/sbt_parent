package com.ajie.oss;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})//让启动类不去加载数据库配置
@ComponentScan(basePackages = {"com.ajie"})
public class OssApplication {

    public static void main(String[] args) {
        SpringApplication.run(OssApplication.class,args);
    }
}
