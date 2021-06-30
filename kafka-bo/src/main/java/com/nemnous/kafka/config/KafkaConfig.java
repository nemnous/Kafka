package com.nemnous.kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.nemnous.kafka.bean.Card;

@Configuration
@EnableKafka
public class KafkaConfig {
    private static final String BOOTSTRAP_SERVER = "127.0.0.1:9092";

    /** Kafka producer Config */
    @Bean
    public ProducerFactory<String, Card> producerFactory() {
        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }

    /**
     * Kafka Template used to serialize Card pojo. (In kafka we need to provide the
     * serializer)
     */
    @Bean
    public KafkaTemplate<String, Card> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    /** Consumer config */
    @Bean
    public ConsumerFactory<String, Card> consumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "com.nemnous.kafka.bean");
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "cards"); // used in the kafka listener. There can be different
                                                             // topics under each group
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Card> kafkaCardListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Card> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
