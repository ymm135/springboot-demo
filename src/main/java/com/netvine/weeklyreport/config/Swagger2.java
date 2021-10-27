package com.netvine.weeklyreport.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2 {
    //配置swagger核心配置
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2) //指定api类型为swagger2
                .apiInfo(apiInfo()) //api详细信息
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.netvine.weeklyreport.controller"))
                .paths(PathSelectors.any())// 对根下所有路径进行监控
                .build();
    }

    public ApiInfo apiInfo(){
        return new ApiInfoBuilder().title("周报系统API")
                .contact(new Contact("mingming","http://netvine.com","1210919685@qq.com"))
                .description("周报系统接口")
                .version("1.0.0")
                .termsOfServiceUrl("http://netvine.com")
                .build();
    }

}
