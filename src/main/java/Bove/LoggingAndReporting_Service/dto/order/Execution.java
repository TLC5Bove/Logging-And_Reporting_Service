package Bove.LoggingAndReporting_Service.dto.order;

import lombok.Data;

import java.util.Date;

@Data
class Execution {
    private Date timestamp;
    private Double price;
    private Integer quantity;
}
