package com.demo.jaxrs.services;

import com.demo.jaxrs.entities.Payment;
import com.demo.jaxrs.entities.PaymentStatus;
import com.demo.jaxrs.entities.PaymentType;
import com.demo.jaxrs.entities.Student;
import com.demo.jaxrs.repositories.PaymentRepository;
import com.demo.jaxrs.repositories.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

@Service
@Transactional
public class PaymentService {

    private StudentRepository studentRepository;

    private PaymentRepository paymentRepository;

    public PaymentService(StudentRepository studentRepository, PaymentRepository paymentRepository) {
        this.studentRepository = studentRepository;
        this.paymentRepository = paymentRepository;
    }

    public Payment savePayment(MultipartFile file, LocalDate date, double amount, PaymentType type, String studentCode) throws IOException {
        // Business logic for managing files in Springboot
        Path folderPath = Paths.get(System.getProperty("user.home"), "payments", "tuition");
        if (!Files.exists(folderPath)) {
            Files.createDirectories(folderPath);
        }
        String fileName = UUID.randomUUID().toString();
        Path filePath = Paths.get(System.getProperty("user.home"), "payments", "tuition", fileName + ".pdf");
        Files.copy(file.getInputStream(), filePath);

        // Create a new payment
        Student student = studentRepository.findByCode(studentCode);
        Payment payment = Payment.builder()
                .date(date)
                .type(type)
                .student(student)
                .amount(amount)
                .file(filePath.toUri().toString())
                .status(PaymentStatus.CREATED)
                .build();
        return paymentRepository.save(payment);
    }

    public byte[] getPaymentFile(Long paymentId) throws IOException {
        Payment payment = paymentRepository.findById(paymentId).get();
        return Files.readAllBytes(Path.of(URI.create(payment.getFile())));
    }

    public Payment updatePaymentStatus(PaymentStatus status, Long id) {
        Payment payment = paymentRepository.findById(id).get();
        payment.setStatus(status);
        return paymentRepository.save(payment);
    }

}
