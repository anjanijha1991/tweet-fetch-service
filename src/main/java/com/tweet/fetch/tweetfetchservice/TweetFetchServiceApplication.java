package com.tweet.fetch.tweetfetchservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableDiscoveryClient
@SpringBootApplication
@EnableWebSecurity
public class TweetFetchServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TweetFetchServiceApplication.class, args);
    }

}
