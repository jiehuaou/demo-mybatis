package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompanyServiceImpl implements CompanyService {
    final DepartmentService departmentService;

    public CompanyServiceImpl(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void testCascadeTransactionFailed(Long id1, Long id2, Long id3, Long id4) {
        departmentService.testSuccessTransaction(id1, id2);
        departmentService.testFailTransaction(id3, id4);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void testCascadeTransactionSuccess(Long id1, Long id2, Long id3, Long id4) {
        departmentService.testSuccessTransaction(id1, id2);
        departmentService.testSuccessTransaction(id3, id4);
    }
}
