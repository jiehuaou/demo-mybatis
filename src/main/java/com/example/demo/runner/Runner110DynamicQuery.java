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
@Order(110)
@Component
public class Runner110DynamicQuery implements CommandLineRunner {
    @Autowired
    EmployeeRepository employeeRepository;
    @Override
    public void run(String... args) throws Exception {
        log.info("-------------- findSome() ------------");
        List<Employee> employees = employeeRepository.selectOnly3();
        employees.forEach(e->log.info("{}", e));

        log.info("-------------- findWithCondition(depId=null, writer) ------------");
        employeeRepository.findWithCondition(null, "writer").forEach(e->log.info("{}", e));

        log.info("-------------- findWithCondition(depId=1L, writer) ------------");
        employeeRepository.findWithCondition(1L, "writer").forEach(e->log.info("{}", e));

        log.info("-------------- findWithCondition2(depId=null, writer) ------------");
        employeeRepository.selectWithCondition(null, "writer").forEach(e->log.info("{}", e));

        log.info("-------------- findWithCondition2(depId=1L, writer) ------------");
        employeeRepository.selectWithCondition(1L, "writer").forEach(e->log.info("{}", e));

        log.info("-------------- findByFirstNameLike('first') ------------");
        employeeRepository.findByFirstNameLike("first").forEach(e->log.info("{}", e));
    }
}
