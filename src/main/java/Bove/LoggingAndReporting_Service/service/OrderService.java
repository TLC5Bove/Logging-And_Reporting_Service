package Bove.LoggingAndReporting_Service.service;

import Bove.LoggingAndReporting_Service.dao.OrderRepo;
import Bove.LoggingAndReporting_Service.dto.order.Execution;
import Bove.LoggingAndReporting_Service.dto.order.message.IdAndExchange;
import Bove.LoggingAndReporting_Service.dto.order.Order;
import Bove.LoggingAndReporting_Service.dto.order.OrderStatusResponse;
import Bove.LoggingAndReporting_Service.dto.order.message.MessageRepo;
import Bove.LoggingAndReporting_Service.mqPubSub.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    OrderRepo orderRepo;

    @Autowired
    ExecutionService executionService;

    @Autowired
    MessageRepo messageRepo;

    @Autowired
    Producer producer;

    @Value("${order.API_KEY}")
    private String exchangeAPIkey;

    @Value("${order.EXCHANGE_URL}")
    private String exchangeURL;
    @Value("${order.EXCHANGE2_URL}")
    private String exchange2URL;

    public List<IdAndExchange> results() {
        return orderRepo.findIDsOfAllPendingOrders();
    }

    public Order findById(String orderId) {
        return orderRepo.findById(orderId).orElse(null);
    }

    public void saveOrder(Order order) {
        orderRepo.save(order);
    }

    public OrderStatusResponse getOrderStatus(String orderId, String exchange) {
        WebClient webClient = WebClient.create("https://exchange.matraining.com");

        OrderStatusResponse response = webClient.get()
                .uri("/" + exchangeAPIkey + "/order/" + orderId)
                .retrieve()
                .bodyToMono(OrderStatusResponse.class)
                .block();

        assert response != null;
        checkOrderExecutionStatus(response, orderId);
        return response;
    }

    private void checkOrderExecutionStatus(OrderStatusResponse response, String orderId) {
//        Order order = findById(orderId);
//
//        if (order == null) return;
//
//        if (response.getExecutions() == order.getExecutions()) return;
//
//        if (Objects.equals(order.getStatus(), "complete")) {
//            messageRepo.deleteById(orderId);
//            return;
//        }

        if (response.getExecutions().isEmpty()) return;

        if (response.getQuantity() >= 1 && response.getQuantity() > response.getCumulatitiveQuantity()) {
            producer.publishCompletedOrdersMessage(response);
        } else {
            messageRepo.deleteById(orderId);
            producer.publishCompletedOrdersMessage(response);
        }
    }
}
