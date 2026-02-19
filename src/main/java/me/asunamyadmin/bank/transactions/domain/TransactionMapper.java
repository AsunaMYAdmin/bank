package me.asunamyadmin.bank.transactions.domain;

import me.asunamyadmin.bank.transactions.data.TransactionEntity;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
    public Transaction toTransaction(TransactionEntity entity) {
        return new Transaction(
                entity.getId(),
                entity.getFromAccount().getId(),
                entity.getToAccount().getId(),
                entity.getAmount(),
                entity.getStatus(),
                entity.getCreatedAt()
        );
    }

    public TransactionEntity toTransactionEntity(Transaction transaction) {
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setAmount(transaction.amount());
        return transactionEntity;
    }
}
