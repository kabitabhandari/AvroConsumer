package com.myavro.consumingwithaavro;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
@Service
@Slf4j
public class ConsumerClass {

    @KafkaListener(topics = "${spring.kafka.consumer.consumer-coffee.topic}", containerFactory = "coffeeFactory")
    public void consumeDetails(ConsumerRecord consumerRecord) {
        log.info("Received message with key={}, partition={}, offset={} and value={} on topic={}",
                consumerRecord.key(),
                consumerRecord.partition(),
                consumerRecord.offset(),
                consumerRecord.value(),
                consumerRecord.topic());

        if (isValidRecord(consumerRecord)) {
            System.out.println("[Consumed Message]:: " + consumerRecord.value());
        }

    }

    private boolean isValidRecord(ConsumerRecord consumerRecord) {
        return consumerRecord != null && consumerRecord.value() != null;
    }

}
