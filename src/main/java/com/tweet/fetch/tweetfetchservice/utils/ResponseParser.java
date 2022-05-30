package com.tweet.fetch.tweetfetchservice.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tweet.fetch.tweetfetchservice.model.User;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@UtilityClass
public class ResponseParser {

    private ObjectMapper mapper = new ObjectMapper();

    private TypeReference<Map<String, Object>> mapTypeReference = new TypeReference<>(){};

    private TypeReference<List<Map<String, String>>> listTypeReference = new TypeReference<>(){};

    public List<Map<String, String>> parseTweet(String response) throws JsonProcessingException {
        if(response.isEmpty()) return new ArrayList<>();

        Map<String, Object> object = mapper.readValue(response, mapTypeReference);

        return mapper.convertValue(object.get("data"), listTypeReference);
    }

    public static List<User> parseUser(String response) throws JsonProcessingException {
        List<User> users = new ArrayList<>();

        if(response.isEmpty()) return users;

        Map<String, Object> object = mapper.readValue(response, mapTypeReference);

        List<Map<String, String>> data = mapper.convertValue(object.get("data"), listTypeReference);

        return data.stream()
                .map(m -> User.builder().userId(m.get("id"))
                        .description(m.get("description"))
                        .userName(m.get("username"))
                        .name(m.get("name"))
                        .createdAt(m.get("created_at"))
                        .build())
                .collect(Collectors.toList());
    }
}
