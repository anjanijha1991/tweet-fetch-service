package com.tweet.fetch.tweetfetchservice.config;

import com.tweet.fetch.tweetfetchservice.model.User;
import com.tweet.fetch.tweetfetchservice.service.KafkaProducerService;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jackson.JsonObjectSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value("${kafka_server.uri}")
    private String KAFKA_URI; // "127.0.0.1:9092"

    @Value("${tweet_topic}")
    private String topic;

    @Bean
    public ProducerFactory producerFactory(){
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_URI);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonObjectSerializer.class);
        return new DefaultKafkaProducerFactory(config);
    }

    @Bean
    public KafkaTemplate kafkaTemplate(){
        return new KafkaTemplate(producerFactory());
    }

    @Bean
    public KafkaProducerService<User> kafkaProducerService(){
        return new KafkaProducerService<>(topic);
    }

    @Bean
    public KafkaProducerService<Map<String, String>> kafkaProducerServiceMap(){
        return new KafkaProducerService<>(topic);
    }
}
