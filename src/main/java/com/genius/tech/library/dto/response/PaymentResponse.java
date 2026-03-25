package com.genius.tech.library.dto.response;

import com.genius.tech.library.enums.PaymentMethod;
import com.genius.tech.library.enums.PaymentStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Returned after POST /students/{id}/payments and in payment history lists.
 */

public class PaymentResponse {

    private Long transactionId;
    private String transactionCode;
    private Long studentId;
    private String studentName;
    private String userCode;
    private BigDecimal amount;
    private PaymentStatus status;
    private PaymentMethod paymentMethod;
    private LocalDateTime paidAt;
    private LocalDateTime createdAt;

    public PaymentResponse() {
    }

    public PaymentResponse(Long transactionId, String transactionCode, Long studentId, String studentName, String userCode, BigDecimal amount, PaymentStatus status, PaymentMethod paymentMethod, LocalDateTime paidAt, LocalDateTime createdAt) {
        this.transactionId = transactionId;
        this.transactionCode = transactionCode;
        this.studentId = studentId;
        this.studentName = studentName;
        this.userCode = userCode;
        this.amount = amount;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.paidAt = paidAt;
        this.createdAt = createdAt;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}