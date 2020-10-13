package com.lzy.cloud.microservicesimpleconsumermovie.feign;

import com.lzy.cloud.microservicesimpleconsumermovie.config.FeignLogConfiguration;
import com.lzy.cloud.microservicesimpleconsumermovie.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/*
使用 @FeignClient 的fallback属性指定回退类
 */
@FeignClient(name = "microservice-simple-provider-user", configuration = FeignLogConfiguration.class,
fallback = FeignClientFallback.class)
public interface UserFeignClient {
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User findById(@PathVariable("id") Long id);
}

/*
回退类需要实现 UserFeignClient接口
 */
@Component
class FeignClientFallback implements UserFeignClient {

    @Override
    public User findById(Long id) {
        User user = new User();
        user.setId(-1l);
        user.setName("默认用户");
        return user;
    }
}
