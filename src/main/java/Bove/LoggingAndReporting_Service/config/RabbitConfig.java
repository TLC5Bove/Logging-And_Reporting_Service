package Bove.LoggingAndReporting_Service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String TRACKING_QUEUE = "tracking_queue";
    public static final String COMPLETION_QUEUE = "completion_queue";
    public static final String CANCEL_FROM_OPS_QUEUE = "canc_from_ops_queue";
    public static final String TRACKING_EXCHANGE = "tracking_exchange";
    public static final String COMPLETION_EXCHANGE = "completion_exchange";
    public static final String CANC_FROM_OPS_EXCHANGE = "canc_from_ops_exchange";
    public static final String ROUTING_KEY = "message_routingKey";


    @Bean
    public Queue tracking_queue() {
        return  new Queue(TRACKING_QUEUE);
    }

    @Bean
    public Queue completion_queue() {
        return  new Queue(COMPLETION_QUEUE);
    }

    @Bean
    public Queue canc_from_ops_queue() {
        return  new Queue(CANCEL_FROM_OPS_QUEUE);
    }

    @Bean
    public DirectExchange tracking_exchange() {
        return new DirectExchange(TRACKING_EXCHANGE);
    }

    @Bean
    public DirectExchange completion_exchange() {
        return new DirectExchange(COMPLETION_EXCHANGE);
    }

    @Bean
    public DirectExchange rec_cancel_exchange() {
        return new DirectExchange(CANC_FROM_OPS_EXCHANGE);
    }

    @Bean
    public Binding tracking_binding() {
        return BindingBuilder
                .bind(tracking_queue())
                .to(tracking_exchange())
                .with(ROUTING_KEY);
    }

    @Bean
    public Binding completion_binding() {
        return BindingBuilder
                .bind(completion_queue())
                .to(completion_exchange())
                .with(ROUTING_KEY);
    }

    @Bean
    public Binding rec_cancel_binding() {
        return BindingBuilder
                .bind(canc_from_ops_queue())
                .to(rec_cancel_exchange())
                .with(ROUTING_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        return  new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return  template;
    }
}
