package Bove.LoggingAndReporting_Service.config;

import Bove.LoggingAndReporting_Service.dto.order.message.IdAndExchange;
import Bove.LoggingAndReporting_Service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Configuration
@EnableScheduling
public class Config {
    @Autowired
    RedisTemplate<String, IdAndExchange> redisTemplate;

    private final String setReference = "OpenIds";

    @Autowired
    OrderService orderService;


    @Scheduled(fixedDelay = 1000)
    public void checkOrderStatus() {
        List<IdAndExchange> results = orderService.results();
        if (results.size() < 50) {
            results.stream().forEach(res -> {
                orderService.getOrderStatus(res.getId(), res.getExchange());
            });
        } else {
            results.parallelStream().forEach(res -> {
                orderService.getOrderStatus(res.getId(), res.getExchange());
            });
        }
    }
}
