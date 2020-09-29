package com.lzy.cloud.microservicesimpleconsumermovie.config;

import feign.Contract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * 该类不应该在 应用程序上下文的 @ComponentScan中
 */
@Configuration
public class FeignConfiguration {


    @Bean
    public Contract feignContract() {
        return new Contract.Default();
    }
}
