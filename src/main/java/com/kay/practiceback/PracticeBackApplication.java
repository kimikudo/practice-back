package com.kay.practiceback;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
@EnableScheduling
@EnableAsync
@MapperScan("com.kay.practiceback.db.*.mapper")
public class PracticeBackApplication {
    public static void main(String[] args) {
        SpringApplication.run(PracticeBackApplication.class, args);
    }

}
