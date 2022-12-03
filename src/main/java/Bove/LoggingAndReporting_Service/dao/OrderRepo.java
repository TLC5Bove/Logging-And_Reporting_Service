package Bove.LoggingAndReporting_Service.dao;

import Bove.LoggingAndReporting_Service.dto.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order, String> {

    @Query("select id from T_order where status = 'pending' or status = 'partial'")
    public List<String> findIDsOfAllPendingOrders();
}
