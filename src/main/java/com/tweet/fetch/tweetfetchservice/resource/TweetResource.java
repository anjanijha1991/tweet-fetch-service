package com.tweet.fetch.tweetfetchservice.resource;

import com.tweet.fetch.tweetfetchservice.model.Tweet;
import com.tweet.fetch.tweetfetchservice.model.User;
import com.tweet.fetch.tweetfetchservice.service.FetchTweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tweet-fetch")
public class TweetResource {

    @Autowired
    FetchTweetService tweetService;

    @GetMapping("/user-details")
    public DeferredResult<List<User>> getUsers(@RequestParam("userNames") String userNames) {
        DeferredResult<List<User>> res = new DeferredResult<>();
        List<User> result = null;
        try {
            result = tweetService.fetchUser(userNames);
        } catch (IOException | URISyntaxException e) {
            res.setErrorResult(e.getMessage());
        }
        res.setResult(result);
        return res;
    }

    @GetMapping("/all-user-timeline")
    public DeferredResult<List<Map<String, String>>> getTimelines() {
        DeferredResult<List<Map<String, String>>> res = new DeferredResult<>();
        List<Map<String, String>> result = null;
        result = tweetService.fetchAllTweets();
        res.setResult(result);
        return res;
    }

    @GetMapping("/user-timeline")
    public DeferredResult<List<Map<String, String>>> getTimeline(@RequestParam("userName") String userName) {
        DeferredResult<List<Map<String, String>>> res = new DeferredResult<>();
        List<Map<String, String>> result = null;
        try {
            result = tweetService.fetchTweet(userName);
        } catch (IOException | URISyntaxException e) {
            res.setErrorResult(e.getMessage());
        }
        res.setResult(result);
        return res;
    }
}
