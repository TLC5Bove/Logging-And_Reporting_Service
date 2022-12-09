package Bove.LoggingAndReporting_Service.controller;

import Bove.LoggingAndReporting_Service.dao.OrderRepo;
import Bove.LoggingAndReporting_Service.dto.order.message.IdAndExchange;
import Bove.LoggingAndReporting_Service.dto.order.OrderStatusResponse;
import Bove.LoggingAndReporting_Service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderStatusController {
    @Autowired
    OrderService orderService;

    @GetMapping("api/v1/order/{orderId}/{exchange}")
    public OrderStatusResponse getOrderStatus(OrderStatusResponse orderStatusResponse, @PathVariable("orderId") String orderId, @PathVariable("exchange") String exchange) {
        return orderService.getOrderStatus(orderId, exchange);
    }
}