package com.demo.jaxrs.dtos;

import com.demo.jaxrs.entities.PaymentStatus;
import com.demo.jaxrs.entities.PaymentType;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PaymentDTO {

    private Long id;

    private LocalDate date;

    private double amount;

    private PaymentType type;

    private PaymentStatus status;

}
