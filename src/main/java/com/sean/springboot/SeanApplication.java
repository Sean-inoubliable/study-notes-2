package com.sean.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@ServletComponentScan
public class SeanApplication {

    /**
     * @ServletComponentScan 与 @Service
     * 共用的情况下，可能会导致监听器&过滤器等二次扫描加载操作
     */

    public static void main(String[] args) {
        SpringApplication.run(SeanApplication.class, args);
    }

}
