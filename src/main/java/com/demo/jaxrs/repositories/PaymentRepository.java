package com.demo.jaxrs.repositories;

import com.demo.jaxrs.entities.Payment;
import com.demo.jaxrs.entities.PaymentStatus;
import com.demo.jaxrs.entities.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByStudentCode(String code);

    List<Payment> findByStatus(PaymentStatus status);

    List<Payment> findByType(PaymentType type);

}
