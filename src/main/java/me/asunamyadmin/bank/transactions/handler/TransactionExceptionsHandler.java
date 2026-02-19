package me.asunamyadmin.bank.transactions.handler;

import me.asunamyadmin.bank.transactions.exception.TransactionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class TransactionExceptionsHandler {
    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<TransactionExceptionDTO> handleTransactionNotFoundException(TransactionNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new TransactionExceptionDTO(
                e.getMessage(),
                e.getStackTrace(),
                LocalDateTime.now()
        ));
    }
}
