package com.example.demo.runner;

import com.example.demo.mapper.EmployeeRepository;
import com.example.demo.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Order(103)
@Component
public class Runner103SimpleCreate implements CommandLineRunner {
    @Autowired
    EmployeeRepository employeeRepository;
    @Override
    public void run(String... args) throws Exception {
        Employee employee = Employee.builder().career("Software Dev").firstName("Joe").lastName("Trump").build();
        log.info("--------------");
        log.info("create(employee) => {}", employeeRepository.create(employee));
        log.info("--------------");
        log.info("findByFirstName(Joe) => {}", employeeRepository.findByFirstName("Joe"));
    }
}
