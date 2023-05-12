package com.example.demo.runner;

import com.example.demo.mapper.DepartmentRepository;
import com.example.demo.model.Department;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
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

        log.info("-------------- testFailTransactionTemplateManually() --------------");
        testFailTransactionTemplateManually();

        log.info("-------------- testSuccessTransactionTemplateManually() --------------");
        testSuccessTransactionTemplateManually();

        log.info("-------------- findAll() --------------");
        departmentRepository.findAll().forEach(e -> log.info("{}", e.toString()));
    }


    void testSuccessTransactionManually() {
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            departmentRepository.create(Department.builder().id(1501L).departName("a005-Manual-Trans-OK").leader("b005").build());
            departmentRepository.create(Department.builder().id(1502L).departName("a005-Manual-Trans-OK").leader("b005").build());
            transactionManager.commit(txStatus);
        } catch (Exception e) {
            transactionManager.rollback(txStatus);
            log.info("TransactionException ==> {}", e);
        }

    }

    void testFailTransactionManually() {
        TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            departmentRepository.create(Department.builder().id(1601L).departName("a005-Manual-Trans-abort").leader("b005").build());
            departmentRepository.create(Department.builder().id(1602L).departName("a005-Manual-Trans-abort").leader("b005").build());
            abort();
            transactionManager.commit(txStatus);
        } catch (Exception e) {
            transactionManager.rollback(txStatus);
            log.info("TransactionException ==> {}", e.toString());
        }
    }

    void testFailTransactionTemplateManually() {

        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(status -> {
            try {
                departmentRepository.create(Department.builder().id(1701L).departName("a005-Manual-Trans-abort").leader("b005").build());
                departmentRepository.create(Department.builder().id(1702L).departName("a005-Manual-Trans-abort").leader("b005").build());
                abort();
            } catch (Exception e) {
                status.setRollbackOnly();
                log.info("TransactionException ==> {}", e.toString());
            }
            return null;
        });
    }

    void testSuccessTransactionTemplateManually() {

        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(status -> {
            try {
                departmentRepository.create(Department.builder().id(1801L).departName("a005-Manual-Trans-OK").leader("b005").build());
                departmentRepository.create(Department.builder().id(1802L).departName("a005-Manual-Trans-OK").leader("b005").build());
                //abort();
            } catch (Exception e) {
                status.setRollbackOnly();
                log.info("TransactionException ==> {}", e.toString());
            }
            return null;
        });
    }

    void abort() {
        throw new RuntimeException("abort !!!");
    }
}
