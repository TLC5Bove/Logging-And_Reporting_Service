package Bove.LoggingAndReporting_Service.service;

import Bove.LoggingAndReporting_Service.dao.ExecutionRepo;
import Bove.LoggingAndReporting_Service.dto.order.Execution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExecutionService {
    @Autowired
    ExecutionRepo executionRepo;

    public void save(Execution execution) {
        executionRepo.save(execution);
    }
}
