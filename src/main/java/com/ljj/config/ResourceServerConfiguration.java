package com.ljj.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @Description: 资源服务器
 * @Author LeeJack
 * @Date 2019/11/13
 * @Version V1.0
 **/
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    private static final String DEMO_RESOURCE_ID = "order";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        // 配置资源id
        resources.resourceId(DEMO_RESOURCE_ID)
                // 令牌信息一般存储到redis，现在测试使用在内存中新建用户
                //.tokenStore(new RedisTokenStore(redisConnectionFactory))
                // 无状态对登录成功的用户不会创建Session
                .stateless(true);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                //配置order访问控制，必须认证过后才可以访问
                .antMatchers("/order/**")
                .authenticated();
    }
}
