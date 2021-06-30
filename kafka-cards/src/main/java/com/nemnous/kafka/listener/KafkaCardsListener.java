package com.nemnous.kafka.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.nemnous.kafka.bean.Card;


@Service
public class KafkaCardsListener {
	
	@KafkaListener(topics = "Kafka_Example", groupId = "group_id")
    public void consume(String message) {
        System.out.println("Consumed message: " + message);
    }


    @KafkaListener(topics = "CreateBOCards", groupId = "cards",
            containerFactory = "kafkaCardListenerContainerFactory")
    public void consumeJson(Card card) {
        System.out.println("Consumed JSON Message: " + card);
    }
}
