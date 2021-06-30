package com.nemnous.kafka.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.nemnous.kafka.bean.Card;

@Service
public class KafkaBOListener {

    private static final String KAFKA_CARD_LISTENER_CONTAINER_FACTORY = "kafkaCardListenerContainerFactory";
    private static final String CREATE_CARD = "CreateCard";

    @KafkaListener(topics = "Kafka_Example", groupId = "group_id")
    public void consume(String message) {
        System.out.println("Consumed message: " + message);
    }

    @KafkaListener(topics = CREATE_CARD, groupId = "cards", containerFactory = KAFKA_CARD_LISTENER_CONTAINER_FACTORY)
    public void consumeJson(Card card) {
        System.out.println("BO:: Consumed JSON Message: " + card);
    }
}
