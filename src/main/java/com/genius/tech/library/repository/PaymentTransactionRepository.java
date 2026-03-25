package com.genius.tech.library.repository;



import com.genius.tech.library.models.PaymentTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {

    Page<PaymentTransaction> findByStudentIdOrderByCreatedAtDesc(Long studentId, Pageable pageable);

    Optional<PaymentTransaction> findByTransactionCode(String transactionCode);

    /** Latest transaction code for generating the next sequential code */
    @Query("SELECT MAX(p.transactionCode) FROM PaymentTransaction p WHERE p.transactionCode LIKE :prefix%")
    Optional<String> findMaxTransactionCodeWithPrefix(@Param("prefix") String prefix);

    /** Total collected amount in a date range — used by reports */
    @Query("""
        SELECT COALESCE(SUM(p.amount), 0)
        FROM PaymentTransaction p
        WHERE p.status = 'PAID'
          AND p.paidAt BETWEEN :from AND :to
        """)
    BigDecimal sumPaidAmountBetween(
            @Param("from") LocalDateTime from,
            @Param("to")   LocalDateTime to
    );
}
