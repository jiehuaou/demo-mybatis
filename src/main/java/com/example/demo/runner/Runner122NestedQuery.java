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
 * demo lazy loading on to-one and to-many link
 */
@Slf4j
@Order(122)
@Component
public class Runner122NestedQuery implements CommandLineRunner {
    @Autowired
    EmployeeRepository employeeRepository;
    @Override
    public void run(String... args) throws Exception {
        log.info("-------------- selectEmployeeWithNested(1) --------------");
        Employee employee = employeeRepository.selectEmployeeWithNested(1L);
        log.info("simple string => {}", employee.toString());
        log.info("-------------- load associated object -----------");
        log.info("long   string => {}", employee.toLongString());
        log.info("--------------");

    }
}
