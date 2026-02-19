package me.asunamyadmin.bank.accounts.domain;

import me.asunamyadmin.bank.transactions.domain.Transaction;
import me.asunamyadmin.bank.transactions.domain.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class TransferService {
    private final AccountService accountService;
    private final TransactionService transactionService;

    @Autowired
    public TransferService(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }


    @Transactional
    public void startTransfer(Transfer transfer, BigDecimal amount) {
        Transaction transaction = createTransaction(transfer, amount);
        try {
            accountService.transferMoney(transfer, amount);
            transactionService.setStatusSuccess(transaction.id());
        } catch (RuntimeException e) {
            transactionService.setStatusFailed(transaction.id());
            throw e;
        }
    }

    private Transaction createTransaction(Transfer transfer, BigDecimal amount) {
        return transactionService.createTransaction(new Transaction(
                amount,
                transfer.toId(),
                transfer.fromId()
        ));
    }
}
