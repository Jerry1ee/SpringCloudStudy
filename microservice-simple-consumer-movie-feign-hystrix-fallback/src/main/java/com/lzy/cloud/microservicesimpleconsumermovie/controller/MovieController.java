package com.lzy.cloud.microservicesimpleconsumermovie.controller;

import com.lzy.cloud.microservicesimpleconsumermovie.feign.UserFeignClient;
import com.lzy.cloud.microservicesimpleconsumermovie.pojo.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class MovieController {

    @Autowired
    private UserFeignClient userFeignClient;

    @GetMapping("/user/{id}")
    public User findById(@PathVariable Long id){
        return this.userFeignClient.findById(id);
    }

}
