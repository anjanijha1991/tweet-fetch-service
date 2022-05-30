package com.tweet.fetch.tweetfetchservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.annotation.processing.Generated;

@Data
@Document(collection = "tweet-topics")
public class User implements Entity{

    @Id
    private String id;

    private String userName;
    private String description;
    private String name;
    private String userId;
    private String createdAt;

    @Builder
    public User(String userName, String description, String name, String userId, String createdAt) {
        this.userName = userName;
        this.description = description;
        this.name = name;
        this.userId = userId;
        this.createdAt = createdAt;
    }
}
