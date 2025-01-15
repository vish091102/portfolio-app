package com.example.portfolio.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfig {

    @Bean
    public NewTopic tradeTopic() {
        return new NewTopic("trade-topic", 1, (short) 1);
    }
}
