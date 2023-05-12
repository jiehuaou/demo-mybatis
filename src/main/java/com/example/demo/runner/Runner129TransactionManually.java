package com.example.demo.runner;

import com.example.demo.mapper.DepartmentRepository;
import com.example.demo.model.Department;
import com.example.demo.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;


/**
 * Transaction is managed manually.
 */
@Slf4j
@Order(129)
@Component
public class Runner129TransactionManually implements CommandLineRunner {

    @Autowired
    PlatformTransactionManager transactionManager;

    @Autowired
    DepartmentRepository departmentRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info("-------------- testSuccessTransactionManually() --------------");
        testSuccessTransactionManually();

        log.info("-------------- testFailTransactionManually() --------------");
        testFailTransactionManually();

        log.info("-------------- findAll() --------------");
        departmentRepository.findAll().forEach(e -> log.info("{}", e.toString()));
    }


    void testSuccessTransactionManually() {
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            departmentRepository.create(Department.builder().id(1501L).departName("a005-Manual-Transaction-OK").leader("b005").build());
            departmentRepository.create(Department.builder().id(1502L).departName("a005-Manual-Transaction-OK").leader("b005").build());
        } catch (Exception e) {
            transactionManager.rollback(txStatus);
            log.info("TransactionException ==> {}", e);
        }
        if(!txStatus.isCompleted()) {
            transactionManager.commit(txStatus);
        }
    }


    void testFailTransactionManually() {
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            departmentRepository.create(Department.builder().id(1601L).departName("a005-Manual-Transaction-abort").leader("b005").build());
            departmentRepository.create(Department.builder().id(1602L).departName("a005-Manual-Transaction-abort").leader("b005").build());
            throw new RuntimeException("abort !!!");
        } catch (Exception e) {
            transactionManager.rollback(txStatus);
            log.info("TransactionException ==> {}", e);
        }
        if(!txStatus.isCompleted()) {
            transactionManager.commit(txStatus);
        }
    }
}
