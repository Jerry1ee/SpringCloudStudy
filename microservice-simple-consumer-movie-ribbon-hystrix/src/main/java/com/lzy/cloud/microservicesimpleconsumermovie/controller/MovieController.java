package com.lzy.cloud.microservicesimpleconsumermovie.controller;

import com.lzy.cloud.microservicesimpleconsumermovie.pojo.User;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class MovieController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieController.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @HystrixCommand(fallbackMethod = "findByIdFallback", commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
            @HystrixProperty(name="metrics.rollingStats.timeInMilliseconds", value = "10000")
    },
        threadPoolProperties = {
                @HystrixProperty(name="coreSize",value = "1"),
                @HystrixProperty(name = "maxQueueSize", value = "10")
        }
    )
    @GetMapping("/user/{id}")
    public User findById(@PathVariable Long id){
        return this.restTemplate.getForObject("http://microservice-simple-provider-user/" +id, User.class);
    }

    public User findByIdFallback(Long id){
        User user = new User();
        user.setId(-1l);
        user.setName("默认用户");
        return user;
    }

    /*
     * 查询 microservice-simple-provider-user的实例信息
     * @return
     */
    @GetMapping("/user-instance")
    public List<ServiceInstance> showInfo(){
        return this.discoveryClient.getInstances("microservice-simple-provider-user");
    }

    /*
     * 打印选择了哪个节点
     */
    @GetMapping("/log-instance")
    public void logUserInstance(){
        ServiceInstance serviceInstance = loadBalancerClient.choose("microservice-simple-provider-user");
        MovieController.LOGGER.info("{}:{}:{}",serviceInstance.getServiceId(),serviceInstance.getHost(),
                serviceInstance.getPort());
    }

}
