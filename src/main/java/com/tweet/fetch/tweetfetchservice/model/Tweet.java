package com.tweet.fetch.tweetfetchservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tweet implements Entity{
    private String id;
    private String text;
    private String tweetedBy;
    private int noOfRetweets;
    private int noOfLikes;
    private Date createdAt;

}
