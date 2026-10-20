package com.yin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.yin.mapper")
public class MyDemoApplication {
    public static void main (String[] args) {
        SpringApplication.run(MyDemoApplication.class);
    }
}
