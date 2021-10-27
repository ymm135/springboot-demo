package com.netvine.weeklyreport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.netvine.weeklyreport.mapper") // 不要使用org.mybatis.spring.annotation.MapperScan
@ComponentScan(basePackages = {"com.netvine.weeklyreport", "org.n3r.idworker"})
public class WeeklyReportApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeeklyReportApplication.class, args);
    }

}
