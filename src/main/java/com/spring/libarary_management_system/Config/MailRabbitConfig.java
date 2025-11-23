package com.spring.libarary_management_system.Config;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.spring.libarary_management_system.Config.RabbitConstants.*;

@Configuration
public class MailRabbitConfig {

    @Bean
    public TopicExchange mailExchange() {
        return new TopicExchange(MAIL_EXCHANGE);
    }

    @Bean
    public Queue mailResetQueue() {
        return new Queue(MAIL_RESET_QUEUE, true);
    }

    @Bean
    public Queue mailCodeQueue() {
        return new Queue(MAIL_CODE_QUEUE, true);
    }
}