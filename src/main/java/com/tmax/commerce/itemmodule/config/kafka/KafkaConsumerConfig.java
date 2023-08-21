package com.tmax.commerce.itemmodule.config.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfig {
    private final KafkaProperty kafkaProperty;

    /**
     *     @Bean
     *     public ConsumerFactory<String, String> consumerFactoryForSomething() {
     *         Map<String, Object> configProps = new HashMap<>();
     *         configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperty.getBootstrapServers());
     *         configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "testGroup");
     *         configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
     *         configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
     *
     *         return new DefaultKafkaConsumerFactory<>(configProps);
     *     }
     *
     *     @Bean
     *     public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactoryForSomething() {
     *         ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
     *         factory.setConsumerFactory(consumerFactoryForSomething());
     *
     *         return factory;
     *     }
     *
     */
}
