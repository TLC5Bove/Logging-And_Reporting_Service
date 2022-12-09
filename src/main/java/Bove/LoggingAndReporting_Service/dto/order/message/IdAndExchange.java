package Bove.LoggingAndReporting_Service.dto.order.message;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@RedisHash("IdAndExchange")
public class IdAndExchange implements Serializable {
    private String id;
    private String exchange;
}
