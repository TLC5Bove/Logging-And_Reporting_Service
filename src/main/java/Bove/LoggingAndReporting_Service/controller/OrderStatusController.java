package Bove.LoggingAndReporting_Service.controller;

import Bove.LoggingAndReporting_Service.dao.OrderRepo;
import Bove.LoggingAndReporting_Service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderStatusController {
    @Autowired
    OrderRepo orderRepo;

    @Autowired
    OrderService orderService;

    @Scheduled(fixedDelay = 1000)
    public void checkOrderStatus() {
        List<String> ids = orderRepo.findIDsOfAllPendingOrders();
        if (ids.size() < 50) {
            ids.stream().forEach(id -> {
                orderService.getOrderStatus(id);
            });
        } else {
            ids.parallelStream().forEach(id -> {
                orderService.getOrderStatus(id);
            });
        }
    }
}
