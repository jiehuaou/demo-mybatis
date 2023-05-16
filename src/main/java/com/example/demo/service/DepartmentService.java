package com.example.demo.service;

import org.springframework.transaction.annotation.Transactional;

public interface DepartmentService {

    /**
     * create 2 Departments and success
     */
    void testSuccessTransaction(Long id1, Long id2);
    /**
     * create 2 Departments and success
     */
    void testFailTransaction(Long id1, Long id2);

    void createDepartment(Long id1);

    //@Transactional
    void testPartialTransaction(Long failedId, Long succeedId);
}
