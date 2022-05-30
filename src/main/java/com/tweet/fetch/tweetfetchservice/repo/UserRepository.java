package com.tweet.fetch.tweetfetchservice.repo;


import com.tweet.fetch.tweetfetchservice.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserRepository extends MongoRepository<User, Integer> {

    @Query("{userName:'?0'}")
    User findUsersByUserName(String userName);

    @Query("{description: /?0/}")
    List<User> findUsersByDescription(String desc);

}
