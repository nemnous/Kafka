package com.nemnous.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nemnous.kafka.bean.Card;

@RestController
@RequestMapping("/cards")
public class CardsController {
    @Autowired
    private KafkaTemplate<String, Card> kafkaTemplate;

    private static final String TOPIC = "CreateCard";

    @PostMapping
    public String createCard(@RequestBody Card card) {
        System.out.println("publishing card to BO listener");
        kafkaTemplate.send(TOPIC, card);
        return "Card Created Successfully";
    }
}
