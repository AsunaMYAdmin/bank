package me.asunamyadmin.bank.transactions.domain;

import me.asunamyadmin.bank.accounts.data.AccountRepository;
import me.asunamyadmin.bank.accounts.exception.AccountNotFoundException;
import me.asunamyadmin.bank.transactions.data.TransactionEntity;
import me.asunamyadmin.bank.transactions.data.TransactionRepository;
import me.asunamyadmin.bank.transactions.exception.TransactionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionMapper mapper;
    private final AccountRepository accountRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository,
                              TransactionMapper mapper,
                              AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.mapper = mapper;
        this.accountRepository = accountRepository;
    }

    public List<Transaction> getAllTransactions() {
        List<TransactionEntity> transactionEntities = new ArrayList<>(transactionRepository.findAll());
        List<Transaction> transactions = new ArrayList<>();
        for (TransactionEntity entity : transactionEntities) {
            transactions.add(mapper.toTransaction(entity));
        }
        return transactions;
    }

    public Transaction getTransactionById(int id) {
        TransactionEntity entity = getEntityFromRepository(id);
        return mapper.toTransaction(entity);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Transaction createTransaction(Transaction transaction) {
        TransactionEntity transactionEntity = mapper.toTransactionEntity(transaction);
        transactionEntity.setFromAccount(
                accountRepository.findById(transaction.fromAccountId()).orElseThrow(AccountNotFoundException::new));
        transactionEntity.setToAccount(
                accountRepository.findById(transaction.toAccountId()).orElseThrow(AccountNotFoundException::new));
        transactionRepository.save(transactionEntity);
        return  mapper.toTransaction(transactionEntity);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void setStatusSuccess(int id) {
        TransactionEntity entity = getEntityFromRepository(id);
        entity.markSuccessful();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void setStatusFailed(int id) {
        TransactionEntity entity = getEntityFromRepository(id);
        entity.markFailed();
    }

    private TransactionEntity getEntityFromRepository(int id) {
        return transactionRepository.findById(id).orElseThrow(TransactionNotFoundException::new);
    }
}
