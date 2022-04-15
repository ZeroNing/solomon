package com.steven.solomon;


import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDubbo
@EnableCaching
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.steven.solomon")
@MapperScan("com.steven.solomon.mapper")
public class TenancyApplication {

  public static void main(String[] args) {
    SpringApplication.run(TenancyApplication.class, args);
  }
}
