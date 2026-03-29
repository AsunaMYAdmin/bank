package me.asunamyadmin.bank.bank_transaction.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    List<TransactionEntity> findAllByFromAccountId(Integer fromAccountId);

    List<TransactionEntity> findAllByToAccountId(Integer toAccountId);
}
