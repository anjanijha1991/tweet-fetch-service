package com.tweet.fetch.tweetfetchservice.service;


import com.tweet.fetch.tweetfetchservice.model.User;
import com.tweet.fetch.tweetfetchservice.repo.TweetRepository;
import com.tweet.fetch.tweetfetchservice.repo.UserRepository;
import com.tweet.fetch.tweetfetchservice.utils.ResponseParser;
import com.tweet.fetch.tweetfetchservice.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FetchTweetService {

    @Autowired
    KafkaProducerService<User> kafkaProducerService;

    @Autowired
    KafkaProducerService<Map<String, String>> mapKafkaProducerService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TweetRepository tweetRepository;
    @Value("${tweet.bearer-token}")
    private String bearerToken;

    @Async
    public List<User> fetchUser(String userName) throws IOException, URISyntaxException {
        String response = Utils.getUsers(userName, bearerToken);
        List<User> users = ResponseParser.parseUser(response);
        CompletableFuture<Object> completableFuture = new CompletableFuture<>();
        completableFuture.completeAsync(() -> kafkaProducerService.pushData(users));
        completableFuture.completeAsync(() -> userRepository.saveAll(users));
        while (!completableFuture.isDone()) ;
        return users;
    }

    public List<Map<String, String>> fetchAllTweets() {
        List<User> allUsers = userRepository.findAll();
        return allUsers.stream()
                .map(User::getUserId)
                .map(userId -> {
                    try {
                        return this.fetchTweet(userId);
                    } catch (IOException | URISyntaxException e) {
                        log.error("error fetching tweet for userid : {}", userId);
                    }
                    return new ArrayList<Map<String, String>>();
                })
                .flatMap(Collection::parallelStream)
                .collect(Collectors.toList());
    }

    public List<Map<String, String>> fetchTweet(String userName) throws IOException, URISyntaxException {
        User user = userRepository.findUsersByUserName(userName);
        return fetchTweetById(user.getId());
    }

    public List<Map<String, String>> fetchTweetById(String userId) throws IOException, URISyntaxException {
        String tweets = Utils.getTweets(userId, bearerToken);
        List<Map<String, String>> map = ResponseParser.parseTweet(tweets);
        CompletableFuture<Object> completableFuture = new CompletableFuture<>();
        completableFuture.completeAsync(() -> mapKafkaProducerService.pushData(map));
        completableFuture.completeAsync(() -> tweetRepository.saveAll(map));
        while (!completableFuture.isDone()) ;
        return map;
    }

}
