package Bove.LoggingAndReporting_Service.mqReceiver;

import Bove.LoggingAndReporting_Service.config.RabbitConfig;
import Bove.LoggingAndReporting_Service.dto.order.message.IdAndExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public class Receiver {
    private final String setReference = "OpenIds";
    @Autowired
    RedisTemplate<String, IdAndExchange> template;

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void listener(IdAndExchange message) {
        save(message);
        System.out.println(message);
        template.opsForSet();
    }

    private void save(IdAndExchange m){
        template.opsForList().rightPush(setReference, m);
    }
}
