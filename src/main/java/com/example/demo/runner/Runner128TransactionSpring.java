package com.example.demo.runner;

import com.example.demo.mapper.DepartmentRepository;
import com.example.demo.model.Department;
import com.example.demo.service.CompanyService;
import com.example.demo.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Transaction is managed by Spring
 */
@Slf4j
@Order(128)
@Service
public class Runner128TransactionSpring implements CommandLineRunner {
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    SqlSessionFactory sqlSessionFactory;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    CompanyService companyService;
    @Override
    public void run(String... args) throws Exception {
        log.info("-------------- testSuccessTransaction() --------------");
        departmentService.testSuccessTransaction(301L, 302L);

        log.info("-------------- testFailTransaction() --------------");
        try {
            departmentService.testFailTransaction(401L, 402L);
        } catch (Exception e) {
            log.info("Transaction Failed ==> {}", e.toString());
        }

        log.info("-------------- testCascadeTransactionFailed(501L, 502L, 601L, 602L) --------------");
        try {
            companyService.testCascadeTransactionFailed(501L, 502L, 601L, 602L);
        } catch (Exception e) {
            log.info("Transaction Failed ==> {}", e.toString());
        }

        log.info("-------------- testCascadeTransactionSuccess(701L, 702L, 801L, 802L) --------------");
        try {
            companyService.testCascadeTransactionSuccess(701L, 702L, 801L, 802L);
        } catch (Exception e) {
            log.info("Transaction Failed ==> {}", e.toString());
        }

        log.info("-------------- findAll() --------------");
        departmentRepository.findAll().forEach(e->log.info("{}", e.toString()));

    }


}
