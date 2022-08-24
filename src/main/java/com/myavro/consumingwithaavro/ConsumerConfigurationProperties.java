package com.myavro.consumingwithaavro;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.kafka.consumer")
@Data
public class ConsumerConfigurationProperties {

    protected ConsumerCoffee consumerCoffee;
    protected String bootstrapServers;
    protected String keyDeserializer;
    protected String valueDeserializer;
    protected String autoOffsetReset;
    protected String enableAutoCommit;
    protected String maxPollRecords;
    protected String sessionTimeoutMs;
    protected String propertiesIsolationLevel;

    @Data
    public static class ConsumerCoffee {
        protected String groupId;
        protected String topic;

    }

}
