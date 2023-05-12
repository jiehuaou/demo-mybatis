package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface CompanyService {
    void testCascadeTransactionFailed(Long id1, Long id2, Long id3, Long id4);
    void testCascadeTransactionSuccess(Long id1, Long id2, Long id3, Long id4);
}
