package com.lzy.cloud.microservicesimpleconsumermovie.feign;

import com.lzy.cloud.microservicesimpleconsumermovie.config.FeignConfiguration;
import com.lzy.cloud.microservicesimpleconsumermovie.pojo.User;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "microservice-simple-provider-user", configuration = FeignConfiguration.class)
public interface UserFeignClient {
    //使用Feign自带的注解 RequestLine
    @RequestLine("GET /{id}")
    public User findById(@Param("id") Long id);
}
