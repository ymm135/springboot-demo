package com.netvine.weeklyreport.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * @Description 控制台输出 Swagger 接口文档地址
 **/
@Configuration
@Slf4j
public class SwaggerPrintConfig implements ApplicationListener<WebServerInitializedEvent> {
    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        try {
            //获取IP
            String hostAddress = Inet4Address.getLocalHost().getHostAddress();
            //获取端口号
            int port = event.getWebServer().getPort();
            //获取应用名
            String applicationName = event.getApplicationContext().getApplicationName();
            log.info("项目启动启动成功！接口文档地址: http://" + hostAddress + ":" + event.getWebServer().getPort() + applicationName + "/swagger-ui.html");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}

