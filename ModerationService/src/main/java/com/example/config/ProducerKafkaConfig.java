package com.example.config;

import java.util.HashMap;
import java.util.Map;

import dao.ModerationResultDao;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import util.KafkaConstants;

import static util.KafkaConstants.BOOK_SEND_MODERATION_RESULT_TOPIC;

@Configuration
public class ProducerKafkaConfig {
    private ProducerFactory<String, ModerationResultDao> producerFactoryModerationResultDao() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                KafkaConstants.KAFKA_PRODUCER_HOST);
        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, ModerationResultDao> kafkaTemplateBookInfoModerationDao() {
        return new KafkaTemplate<>(producerFactoryModerationResultDao());
    }

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstants.KAFKA_PRODUCER_HOST);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic bookModerationTopic() {
        // TODO move to application properties
        return new NewTopic(BOOK_SEND_MODERATION_RESULT_TOPIC, 2, (short) 1);
    }
}
