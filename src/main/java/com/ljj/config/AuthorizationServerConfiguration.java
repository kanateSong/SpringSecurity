package com.ljj.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @Description: 授权服务器
 * @Author LeeJack
 * @Date 2019/11/13
 * @Version V1.0
 **/
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    RedisConnectionFactory redisConnectionFactory;
    @Autowired
    private UserDetailsService userService;

    @Value("${oauth2.client-id}")
    private String clientId;
    @Value("${oauth2.secret}")
    private String clientSecret;

    private static final String DEMO_RESOURCE_ID = "order";

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //配置两个客户端,一个用于password认证一个用于client认证
        /*clients.inMemory().withClient("client_1")
                .resourceIds(DEMO_RESOURCE_ID)//客户端ID
                .authorizedGrantTypes("client_credentials", "refresh_token")
                .scopes("select")
                .authorities("client") //授权服务器，client_1和client_2都属于client
                .secret("123456")//设置客户端密码
                .and().withClient("client_2")
                .resourceIds(DEMO_RESOURCE_ID)
                .authorizedGrantTypes("password", "refresh_token")
                .scopes("select")
                .authorities("client")
                .secret("123456");*/

        String finalSecret = "{bcrypt}"+passwordEncoder().encode(clientSecret);
        clients.inMemory()
                .withClient(clientId)//客户端ID
                .authorizedGrantTypes("password", "refresh_token","client_credentials")//设置验证方式
                .scopes("read", "write","trust")
                .secret(finalSecret)//设置客户端密码
                .authorities("oauth2")
                .accessTokenValiditySeconds(7200) //token过期时间
                .refreshTokenValiditySeconds(7200); //refresh过期时间
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //使用redis的tokenStore token信息保存到redis
        endpoints.tokenStore(new RedisTokenStore(redisConnectionFactory))
                .authenticationManager(authenticationManager)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                //配置userService 这样每次认证的时候会去检验用户是否锁定，有效等
                .userDetailsService(userService);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        //允许表单认证
        oauthServer.allowFormAuthenticationForClients();
    }

}
