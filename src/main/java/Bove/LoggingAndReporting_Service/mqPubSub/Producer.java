package Bove.LoggingAndReporting_Service.mqPubSub;

import Bove.LoggingAndReporting_Service.config.RabbitConfig;
import Bove.LoggingAndReporting_Service.dto.order.OrderStatusResponse;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Producer {
    @Autowired
    RabbitTemplate rabbitTemplate;

    public void publishCompletedOrdersMessage(OrderStatusResponse message){
        rabbitTemplate.convertAndSend(RabbitConfig.COMPLETION_EXCHANGE, RabbitConfig.ROUTING_KEY, message);
    }}
