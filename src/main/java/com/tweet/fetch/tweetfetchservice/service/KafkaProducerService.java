package com.tweet.fetch.tweetfetchservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
public class KafkaProducerService<T> {

    @Autowired
    KafkaTemplate<String, T> kafkaTemplate;

    private final String topic;

    public KafkaProducerService(String topic) {
        this.topic = topic;
    }

    public boolean pushData(List<T> objList) {
        log.info("produce into kafka started... for # of tweets : " + objList.size());
        objList.parallelStream().forEach(t -> kafkaTemplate.send(topic, t));
//        List<ListenableFuture<SendResult<String, T>>> res = objList.stream()
//                .map(t -> kafkaTemplate.send(topic, t))
//                .toList();
//        long count = 0;
//        while (count < res.size()) {
//            count = res.stream()
//                    .filter(future -> future.isDone() || future.isCancelled())
//                    .map(this::verifyKafkaResult)
//                    .count();
//        }
//        log.info("Done.");
        return true;
    }

//    private boolean verifyKafkaResult(ListenableFuture<SendResult<String, T>> future) {
//        try {
//            SendResult<String, T> sr = future.get();
//            T t = sr.getProducerRecord().value();
//            log.info("object {} is successfully pushed into kafka.", t.toString());
//        } catch (InterruptedException | ExecutionException e) {
//            log.error("error in pushing the tweet to kafka.", e);
//            return false;
//        }
//        return true;
//    }

}
