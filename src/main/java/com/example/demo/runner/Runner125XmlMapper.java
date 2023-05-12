package com.example.demo.runner;

import com.example.demo.mapper.DepartmentRepository;
import com.example.demo.mapper.EmployeeRepository;
import com.example.demo.model.Department;
import com.example.demo.model.Employee;
import com.example.demo.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Order(125)
@Component
public class Runner125XmlMapper implements CommandLineRunner {
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @Override
    public void run(String... args) throws Exception {
        log.info("-------------- create() --------------");
        departmentRepository.create(Department.builder().id(200L).departName("hello").leader("world").build());
        departmentRepository.findById(200L).ifPresent(e->log.info("{}", e.toString()));

        log.info("-------------- findAll() --------------");
        departmentRepository.findAll().forEach(e->log.info("{}", e.toString()));

        log.info("-------------- findByNameLike(\"ort  \") --------------");
        departmentRepository.findByNameLike("ORT  ").forEach(e->log.info("{}", e.toString()));

        log.info("-------------- findByNameLikeAndLeader('ORT  ', 'John') --------------");
        departmentRepository.findByNameLikeAndLeader("ORT  ", "John").forEach(e->log.info("{}", e.toString()));

        log.info("-------------- findByNameIn(sport, motor) --------------");
        departmentRepository.findByNameIn("sport", "motor").forEach(e->log.info("{}", e.toString()));
    }
}
