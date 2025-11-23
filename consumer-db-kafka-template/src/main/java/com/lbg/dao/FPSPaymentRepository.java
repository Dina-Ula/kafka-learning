package com.lbg.dao;

import com.lbg.model.FPSPaymentEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FPSPaymentRepository extends CrudRepository<FPSPaymentEntity, Long> {
    List<FPSPaymentEntity> findByAccountNo(String accountNo);

    List<FPSPaymentEntity> findBySortCode(String sortCode);
}