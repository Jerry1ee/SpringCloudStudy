package com.lzy.cloud.microservicesimpleprovideruser.dao;

import com.lzy.cloud.microservicesimpleprovideruser.po.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Long>{

}