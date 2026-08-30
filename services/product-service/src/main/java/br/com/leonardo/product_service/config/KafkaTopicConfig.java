package br.com.leonardo.product_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic productCreatedTopic() {
        return new NewTopic("product-created", 3, (short) 1);
    }

    @Bean
    public NewTopic productPriceChangedTopic() {
        return new NewTopic("product-price-changed", 3, (short) 1);
    }
}
