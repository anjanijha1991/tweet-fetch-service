package com.tweet.fetch.tweetfetchservice.resource;

import com.tweet.fetch.tweetfetchservice.model.User;
import com.tweet.fetch.tweetfetchservice.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user-info")
public class Controller {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/user-details")
    public List<User> findAllUsers(){
        return userRepository.findAll();
    }
}
