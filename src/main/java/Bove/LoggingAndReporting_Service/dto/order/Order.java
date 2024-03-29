package Bove.LoggingAndReporting_Service.dto.order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.List;
import java.util.Objects;


@Entity(name = "T_order")
@Getter
@Setter
@RequiredArgsConstructor
public class Order {
    private final Date orderDate = new Date();
    @Id
    @Column(name = "id", nullable = false)
    @NotNull
    private String orderID;
    //    private String id;
    private String product;
    private int quantity;
    private Double price;
    private String side;
    private String type;
    private Date dateCreated;
    private Date dateClosed;
    private Date dateUpdated;
    private String status;
    private String exchange;
    private int userId;
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Execution> executions;
    private int cumulatitiveQuantity;
    private double cumulatitivePrice;

    public Order(String id,
                 String product,
                 int quantity,
                 double price,
                 String side,
                 String type,
                 Date dateCreated,
                 String exchange,
                 int userId) {
        this.orderID = id;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.side = side;
        this.type = type;
        this.dateCreated = dateCreated;
        this.exchange = exchange;
        this.userId = userId;
        this.status = "pending";
    }

    public Order (){

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return quantity == order.quantity && userId == order.userId && cumulatitiveQuantity == order.cumulatitiveQuantity && Double.compare(order.cumulatitivePrice, cumulatitivePrice) == 0 && Objects.equals(orderDate, order.orderDate) && orderID.equals(order.orderID) && product.equals(order.product) && price.equals(order.price) && side.equals(order.side) && type.equals(order.type) && dateCreated.equals(order.dateCreated) && Objects.equals(dateClosed, order.dateClosed) && Objects.equals(dateUpdated, order.dateUpdated) && status.equals(order.status) && exchange.equals(order.exchange);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderDate, orderID, product, quantity, price, side, type, dateCreated, dateClosed, dateUpdated, status, exchange, userId, cumulatitiveQuantity, cumulatitivePrice);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderDate=" + orderDate +
                ", orderID='" + orderID + '\'' +
                ", product='" + product + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", side='" + side + '\'' +
                ", type='" + type + '\'' +
                ", dateCreated=" + dateCreated +
                ", dateClosed=" + dateClosed +
                ", dateUpdated=" + dateUpdated +
                ", status='" + status + '\'' +
                ", exchange='" + exchange + '\'' +
                ", userId=" + userId +
                ", cumulatitiveQuantity=" + cumulatitiveQuantity +
                ", cumulatitivePrice=" + cumulatitivePrice +
                '}';
    }
}
