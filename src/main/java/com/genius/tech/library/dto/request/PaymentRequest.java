package com.genius.tech.library.dto.request;

import com.genius.tech.library.enums.PaymentMethod;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

/**
 * Request body for POST /api/students/{id}/payments.
 */
public class PaymentRequest {

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be positive")
    @Digits(integer = 8, fraction = 2)
    private BigDecimal amount;

    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;

    /** Optional reference number (UPI txn id, cheque no., etc.) */
    @Size(max = 100)
    private String referenceNote;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getReferenceNote() {
        return referenceNote;
    }

    public void setReferenceNote(String referenceNote) {
        this.referenceNote = referenceNote;
    }
}
