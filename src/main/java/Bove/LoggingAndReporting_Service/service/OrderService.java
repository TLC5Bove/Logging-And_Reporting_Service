package Bove.LoggingAndReporting_Service.service;

import Bove.LoggingAndReporting_Service.dao.OrderRepo;
import Bove.LoggingAndReporting_Service.dto.order.IdAndExchange;
import Bove.LoggingAndReporting_Service.dto.order.Order;
import Bove.LoggingAndReporting_Service.dto.order.OrderStatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepo orderRepo;

    @Value("${order.API_KEY}")
    private String exchangeAPIkey;

    @Value("${order.EXCHANGE_URL}")
    private String exchangeURL;
    @Value("${order.EXCHANGE2_URL}")
    private String exchange2URL;

    public List<IdAndExchange> results() {
        return orderRepo.findIDsOfAllPendingOrders();
    }

    public OrderStatusResponse getOrderStatus(String orderId, String exchange) {
        WebClient webClient = WebClient.create("https://" + exchange + "matraining.com");

        OrderStatusResponse response = webClient.get()
                .uri("/" + exchange + "/order/" + orderId)
                .retrieve()
                .bodyToMono(OrderStatusResponse.class)
                .block();

        assert response != null;
        checkOrderExecutionStatus(response, orderId);

        return response;
    }

    private String checkOrderExecutionStatus(OrderStatusResponse response, String orderId) {

        Order order = orderRepo.findById(orderId).get();
        if (order.getStatus() == "complete") return "";

        Integer unexecutedQuantity = response.getQuantity() - response.getCumulatitiveQuantity();
        if (response.getExecutions() == null) {
            order.setStatus("pending");
            order.setDateUpdated(new Date());
            orderRepo.save(order);
            return "This order with ID " + response.getOrderID() + " has not been executed";
        } else if (response.getQuantity() >= 1 && response.getQuantity() > response.getCumulatitiveQuantity()) {
            order.setStatus("partial");
            order.setDateUpdated(new Date());
            orderRepo.save(order);
            return "This order with ID " + response.getOrderID() + " has been partially executed \n " + "" + unexecutedQuantity + "to " + response.getSide();
        } else {
            order.setStatus("complete");
            order.setDateUpdated(new Date());
            orderRepo.save(order);
            return "This order with ID " + response.getOrderID() + " has been fully executed";
        }
    }
}
