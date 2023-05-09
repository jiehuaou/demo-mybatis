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
@Order(101)
@Component
public class RunnerSimpleUpdate implements CommandLineRunner {
    @Autowired
    EmployeeRepository employeeRepository;
    @Override
    public void run(String... args) throws Exception {

        log.info("--------------");
        log.info("updateDep(8, 2) => {}", employeeRepository.updateDep(8L, 2L));
        log.info("--------------");
        log.info("findById(8) => {}", employeeRepository.findById(8L));
    }
}
