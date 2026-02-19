package me.asunamyadmin.bank.transactions.job;

import me.asunamyadmin.bank.transactions.data.TransactionRepository;
import me.asunamyadmin.bank.transactions.domain.TransactionService;
import me.asunamyadmin.bank.transactions.domain.TransactionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class PendingTransactionJob {
    private final TransactionRepository transactionRepository;
    private final TransactionService transactionService;

    @Autowired
    public PendingTransactionJob(TransactionRepository transactionRepository, TransactionService transactionService) {
        this.transactionRepository = transactionRepository;
        this.transactionService = transactionService;
    }

    @Scheduled(fixedRate = 30000)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void checkOldPendingTransactions() {
        LocalDateTime oldTime = LocalDateTime.now().minusMinutes(1);

        List<Integer> expiredIds = transactionRepository.findAllExpiredPendingTransactions(
                TransactionStatus.PENDING,
                oldTime
        );

        for (Integer id : expiredIds) {
            transactionService.setStatusFailed(id);
        }
    }
}