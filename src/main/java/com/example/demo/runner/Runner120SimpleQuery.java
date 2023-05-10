package com.example.demo.runner;

import com.example.demo.mapper.EmployeeRepository;
import com.example.demo.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Order(120)
@Component
public class Runner120SimpleQuery implements CommandLineRunner {
    @Autowired
    EmployeeRepository employeeRepository;
    @Override
    public void run(String... args) throws Exception {
        log.info("-------------- findAll() --------------");
        List<Employee> employees = employeeRepository.findAll();
        employees.forEach(e->log.info("{}", e));
        log.info("--------------");
        log.info("findById() => {}", employeeRepository.findById(1L));
        log.info("--------------");
        log.info("findById() => {}", employeeRepository.findById2(2L));
    }
}
