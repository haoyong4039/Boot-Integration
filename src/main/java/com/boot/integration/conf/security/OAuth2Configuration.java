package com.boot.integration.conf.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

@Configuration
public class OAuth2Configuration
{

    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter
    {

        @Autowired
        private MyAuthenticationEntryPoint myAuthenticationEntryPoint;

        @Override
        public void configure(HttpSecurity http) throws Exception
        {
            http.exceptionHandling()
                .authenticationEntryPoint(myAuthenticationEntryPoint)
                .and()
                .authorizeRequests()
                .antMatchers("/static/**","/chat-room/**","/object/**","/druid/**")
                .permitAll()
                .anyRequest()
                .authenticated();
        }
    }

    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter
    {

        @Autowired
        private DataSource dataSource;

        @Bean
        public TokenStore tokenStore()
        {
            return new JdbcTokenStore(dataSource);
        }

        //        @Autowired
        //        private RedisConnectionFactory redisConnectionFactory;
        //
        //        @Bean
        //        public TokenStore tokenStore() {
        //            return new RedisTokenStore(redisConnectionFactory);
        //        }

        @Autowired
        @Qualifier("authenticationManagerBean")
        private AuthenticationManager authenticationManager;

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints)
        {
            endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManager);
        }

        @Override
        public void configure(AuthorizationServerSecurityConfigurer oauthServer)
        {
            //允许表单认证
            oauthServer.allowFormAuthenticationForClients();
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception
        {
            clients.inMemory()
                .withClient("client")
                .scopes("read", "write")
                .authorities("ROLE_ADMIN", "ROLE_USER")
                .authorizedGrantTypes("password", "refresh_token")
                .secret("secret")
                .accessTokenValiditySeconds(60)
                .refreshTokenValiditySeconds(60 * 60 * 24);
        }
    }

}
