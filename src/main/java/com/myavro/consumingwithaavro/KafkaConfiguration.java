package com.myavro.consumingwithaavro;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

import static org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG;

/*
Springboot provides an auto configuration for Kafka via KafkaAutoConfiguration class.
When you use @EnableAutoConfiguration or @SpringBootApplication, Spring boot automatically configures Kafka for you.
If you don't use Springboot, then you would have to use @EnableKafka to configure Kafka for your Spring app.
 */


@EnableKafka
@Configuration
public class KafkaConfiguration {
    protected  ConsumerConfigurationProperties consumerConfigurationProperties;

    @Autowired
    public KafkaConfiguration(ConsumerConfigurationProperties consumerConfigurationProperties) {

        this.consumerConfigurationProperties = consumerConfigurationProperties;
    }

    @Bean
    public ConsumerFactory<String, Student> StudentConsumerFactory() {
        //The following Keys are found in ConsumerConfig Class and the package is org.apache.kafka.clients.consumer

        Map<String, Object> consumerProperties = new HashMap<>();

        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, consumerConfigurationProperties.getBootstrapServers());
        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, consumerConfigurationProperties.consumerCoffee.getGroupId());

        consumerProperties.put(KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        consumerProperties.put(VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);

        consumerProperties.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, consumerConfigurationProperties.getKeyDeserializer());
        consumerProperties.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, consumerConfigurationProperties.getValueDeserializer());

       // consumerProperties.put("spring.kafka.consumer.auto-offset-reset" , "earliest");
        consumerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG , consumerConfigurationProperties.getAutoOffsetReset());
        consumerProperties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG , consumerConfigurationProperties.getEnableAutoCommit());
        consumerProperties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG , consumerConfigurationProperties.getMaxPollRecords());
        consumerProperties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG , consumerConfigurationProperties.getSessionTimeoutMs());
        consumerProperties.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG , consumerConfigurationProperties.getPropertiesIsolationLevel());

        consumerProperties.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, "false");
        consumerProperties.put("schema.registry.url","http://127.0.0.1:8081" );

        return new DefaultKafkaConsumerFactory<>(consumerProperties);
    }

    @Bean("coffeeFactory")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Student>> StudentKafkaListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Student> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(StudentConsumerFactory());
        return factory;
    }

}
