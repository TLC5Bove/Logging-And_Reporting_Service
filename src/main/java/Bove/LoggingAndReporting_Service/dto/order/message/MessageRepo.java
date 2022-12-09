package Bove.LoggingAndReporting_Service.dto.order.message;

import org.springframework.data.repository.CrudRepository;

public interface MessageRepo extends CrudRepository<IdAndExchange, String> {
}
