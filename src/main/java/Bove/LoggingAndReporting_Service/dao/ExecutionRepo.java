package Bove.LoggingAndReporting_Service.dao;

import Bove.LoggingAndReporting_Service.dto.order.Execution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExecutionRepo extends JpaRepository<Execution, Integer> {
}
