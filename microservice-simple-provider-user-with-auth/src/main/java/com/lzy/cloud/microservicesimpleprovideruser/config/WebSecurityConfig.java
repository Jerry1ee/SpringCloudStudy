package com.lzy.cloud.microservicesimpleprovideruser.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //所有请求都需要经过 HTTP basic认证
        http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        //明文编码器。这是一个不做任何操作的密码编码器，是Spring提供给我们做明文测试的
        return NoOpPasswordEncoder.getInstance();
    }

    @Autowired
    private CustomUserDetailService userDetailService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailService).passwordEncoder(this.passwordEncoder());
    }
    @Component
    class CustomUserDetailService implements UserDetailsService{
        /*
         * 模拟两个账户：
         * 1.账号是user，密码是password1，角色是user-role
         * 2.账号是admin，密码是password2，角色是admin-role
         */

        @Override
        public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
            if("user".equals(userName)){
                return new SecurityUser("user","password1","user-role");
            }else if("admin".equals(userName)){
                return new SecurityUser("admin","password2","admin-role");
            }else{
                return null;
            }
        }
    }
    class SecurityUser implements UserDetails {
        private static final long serialVersionUID = 1L;

        private Long id;
        private String userName;
        private String password;
        private String role;
        public SecurityUser(String userName, String password, String role){
            super();
            this.userName = userName;
            this.password = password;
            this.role = role;
        }

        public SecurityUser(){
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            Collection<GrantedAuthority> authorities = new ArrayList();
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(this.role);
            authorities.add(authority);
            return authorities;
        }

        @Override
        public String getPassword() {
            return null;
        }

        @Override
        public String getUsername() {
            return null;
        }

        @Override
        public boolean isAccountNonExpired() {
            return false;
        }

        @Override
        public boolean isAccountNonLocked() {
            return false;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return false;
        }

        @Override
        public boolean isEnabled() {
            return false;
        }

    }
}
