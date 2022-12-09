package Bove.LoggingAndReporting_Service.config;

import Bove.LoggingAndReporting_Service.dto.order.message.IdAndExchange;
import Bove.LoggingAndReporting_Service.dto.order.message.MessageRepo;
import Bove.LoggingAndReporting_Service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Configuration
@EnableScheduling
public class Config {
    @Autowired
    RedisTemplate<String, IdAndExchange> template;

    @Autowired
    OrderService orderService;

    @Autowired
    MessageRepo messageRepo;

    @Scheduled(fixedDelay = 1000)
    public void checkOrderStatus() {
        Iterable<IdAndExchange> result = messageRepo.findAll();
        List<IdAndExchange> res = new ArrayList<>();

        for (var i : result) {
            res.add(i);
        }

        if (res.size() < 50) {
            res.forEach(r -> {
                orderService.getOrderStatus(r.getId(), r.getExchange());
            });
        } else {
            res.parallelStream().forEach(r -> {
                orderService.getOrderStatus(r.getId(), r.getExchange());
            });
        }
    }
}
