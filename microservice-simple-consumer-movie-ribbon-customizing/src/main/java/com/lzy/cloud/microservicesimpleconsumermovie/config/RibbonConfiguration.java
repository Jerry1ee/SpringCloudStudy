package com.lzy.cloud.microservicesimpleconsumermovie.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * 该类不应该在主应用程序上下文的 @ComponentScan 中，应该手动排除不被扫描
 */
@Configuration
public class RibbonConfiguration {
    @Bean
    public IRule ribbonRule() {
        //修改负载均衡规则为随机
        return new RandomRule();
    }
}
