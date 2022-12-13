package Bove.LoggingAndReporting_Service.mqPubSub;

import Bove.LoggingAndReporting_Service.config.RabbitConfig;
import Bove.LoggingAndReporting_Service.dto.order.message.IdAndExchange;
import Bove.LoggingAndReporting_Service.dto.order.message.MessageRepo;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class Receiver {
    @Autowired
    RedisTemplate<String, IdAndExchange> template;
    @Autowired
    MessageRepo messageRepo;

    @RabbitListener(queues = RabbitConfig.TRACKING_QUEUE)
    public void listener(IdAndExchange message) {
        messageRepo.save(message);
    }

    @RabbitListener(queues = RabbitConfig.CANCEL_FROM_OPS_QUEUE)
    public void cancelListener(String id){
        messageRepo.deleteById(id);
    }
}
