package me.asunamyadmin.bank.bank_transaction.service;

import me.asunamyadmin.bank.bank_transaction.data.TransactionEntity;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
    public TransactionDTO getTransactionFromEntity(TransactionEntity entity) {
        return new TransactionDTO(
                entity.getFromAccountId(),
                entity.getToAccountId(),
                entity.getAmount(),
                entity.getCurrency(),
                entity.getType(),
                entity.getStatus()
        );
    }
}
