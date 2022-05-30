package com.tweet.fetch.tweetfetchservice.repo;

import com.tweet.fetch.tweetfetchservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Map;

public interface TweetRepository extends MongoRepository<Map<String, String>, Integer> {
}
