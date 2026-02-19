package me.asunamyadmin.bank.transactions.data;

import me.asunamyadmin.bank.transactions.domain.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Integer> {
    @Query("select t.id from TransactionEntity t where t.status = :status and t.createdAt < :oldTime ")
    List<Integer> findAllExpiredPendingTransactions(
                @Param("status") TransactionStatus status,
                @Param("oldTime") LocalDateTime oldTime
            );
}
