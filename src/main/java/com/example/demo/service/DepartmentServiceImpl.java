package com.example.demo.service;

import com.example.demo.mapper.DepartmentRepository;
import com.example.demo.model.Department;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * spring Manage Transaction
 */
@Service
public class DepartmentServiceImpl implements DepartmentService{

    final private DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    @Transactional
    public void testSuccessTransaction(Long id1, Long id2) {
        departmentRepository.create(Department.builder().id(id1).departName("a001-transaction-OK").leader("b001").build());
        departmentRepository.create(Department.builder().id(id2).departName("a002-transaction-OK").leader("b002").build());
    }

    @Override
    @Transactional
    public void testFailTransaction(Long id1, Long id2) {
        departmentRepository.create(Department.builder().id(id1).departName("a005-transaction-fail").leader("b005").build());
        departmentRepository.create(Department.builder().id(id2).departName("a006-transaction-fail").leader("b006").build());
        throw new RuntimeException("Something wrong !!!");
    }

}
