package com.example.demo.runner;

import com.example.demo.mapper.EmployeeRepository;
import com.example.demo.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * construct object graph with Join Query.
 */
@Slf4j
@Order(121)
@Component
public class Runner121JoinQuery implements CommandLineRunner {
    @Autowired
    EmployeeRepository employeeRepository;
    @Override
    public void run(String... args) throws Exception {
        log.info("-------------- findAllWithJoin() --------------");
        List<Employee> employees = employeeRepository.findAllWithJoin();
        employees.forEach(e->log.info("{}", e.toLongString()));
        log.info("--------------");

    }
}
