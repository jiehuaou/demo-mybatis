package com.example.demo.service;

import ch.qos.logback.core.joran.util.beans.BeanDescriptionFactory;
import com.example.demo.mapper.DepartmentRepository;
import com.example.demo.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AnotherService {
    @Autowired
    DepartmentRepository departmentRepository;
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createAnotherDepartment(Long id2) {

        departmentRepository.create(Department.builder().id(id2).departName("a001-createAnotherDepartment").leader("b001").build());
        //throw new RuntimeException("create Department Abort !!!");
    }
}
