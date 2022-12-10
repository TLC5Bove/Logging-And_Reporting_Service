package Bove.LoggingAndReporting_Service.dto.order;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class OrderStatusResponse {
    private String product;
    private Integer quantity;
    private Double price;
    private String side;
    private List<Execution> executions;
    private String orderID;
    private String orderType;
    private Integer cumulatitiveQuantity;
    private Double cumulatitivePrice;
}

