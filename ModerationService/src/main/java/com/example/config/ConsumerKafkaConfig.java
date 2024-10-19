package com.example.config;

import java.util.HashMap;
import java.util.Map;

import dao.BookInfoModerationDao;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import util.KafkaConstants;

@Configuration
public class ConsumerKafkaConfig {
    private final static String CONSUMER_GROUP_ID = "book-send-moderation-consumer-group";

    private ConsumerFactory<String, BookInfoModerationDao> consumerFactoryBookInfoModerationDao() {
        Map<String, Object> props = new HashMap<>();
        props.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                KafkaConstants.KAFKA_CONSUMER_HOST);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                CONSUMER_GROUP_ID);
        props.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        props.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(),
                new JsonDeserializer<>(BookInfoModerationDao.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, BookInfoModerationDao> kafkaListenerContainerFactoryMessage() {
        ConcurrentKafkaListenerContainerFactory<String, BookInfoModerationDao> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryBookInfoModerationDao());
        return factory;
    }
}
