package com.example.demo.runner;

import com.example.demo.mapper.DepartmentRepository;
import com.example.demo.mapper.EmployeeRepository;
import com.example.demo.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Order(125)
@Component
public class Runner125Session implements CommandLineRunner {
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    SqlSessionFactory sqlSessionFactory;
    @Override
    public void run(String... args) throws Exception {
        log.info("-------------- findAll() --------------");
        departmentRepository.findAll().forEach(e->log.info("{}", e.toString()));
    }
}
