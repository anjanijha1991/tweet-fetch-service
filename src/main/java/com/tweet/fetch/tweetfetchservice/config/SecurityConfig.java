package com.tweet.fetch.tweetfetchservice.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(this::authorizeRequests)
                .oauth2Login();
        return http.build();
    }

    private void authorizeRequests(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authz) {
        try {
            authz
                    .antMatchers("/").permitAll()
                    .antMatchers("/tweet-fetch/**")
                    .authenticated()
                    .and()
                    .oauth2Login();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

