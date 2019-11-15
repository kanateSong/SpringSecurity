package com.ljj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @Description: 对SpringSecurity的配置的扩展，支持自定义白名单资源路径和查询用户逻辑
 * @Author LeeJack
 * @Date 2019/11/13
 * @Version V1.0
 **/
@Configuration
@EnableWebSecurity
@Order(1000) //可以设置优先级
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Bean
    @Override
    //配置在内存中的两个用户
    protected UserDetailsService userDetailsService() {
        //替换掉了容器中的UserDetailsService
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user_1").password("123456").authorities("USER").build());
        manager.createUser(User.withUsername("user_2").password("123456").authorities("USER").build());
        return manager;
    }

    @Override
    //配置在内存中的两个用户，效果跟上面一样
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //替换了AuthenticationManager效果
        auth.inMemoryAuthentication()
                .withUser("user_1")
                .password("123456")
                .authorities("USER")
                .and()
                .withUser("user_2")
                .password("123456")
                .authorities("USER");

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        //不重写configure(AuthenticationManagerBuilder auth)注入内存中的用户会被新的管理覆盖
        return super.authenticationManagerBean();
    }

    //配置不需要权限验证的路径
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http.requestMatchers().anyRequest()
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/*")
                .permitAll();
        // @formatter:on
    }
}
