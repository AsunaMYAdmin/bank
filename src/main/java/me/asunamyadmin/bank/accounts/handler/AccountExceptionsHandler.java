package me.asunamyadmin.bank.accounts.handler;

import me.asunamyadmin.bank.accounts.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class AccountExceptionsHandler {
    LocalDateTime CURRENT_TIME = LocalDateTime.now();
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<AccountExceptionsDTO> handleAccountNotFoundException(AccountNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(getAccountExceptionsDTO(ex));
    }
    @ExceptionHandler(AccountBlockedException.class)
    public ResponseEntity<AccountExceptionsDTO> handleAccountBlockedException(AccountBlockedException ex){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(getAccountExceptionsDTO(ex));
    }
    @ExceptionHandler(exception = {
            SelfTransferException.class,
            AccountHasAlreadyBeenBlocked.class,
            InsufficientFundsException.class
    })
    public ResponseEntity<AccountExceptionsDTO> handleSelfTransferException(SelfTransferException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getAccountExceptionsDTO(ex));
    }

    private AccountExceptionsDTO getAccountExceptionsDTO(RuntimeException exception) {
        return new AccountExceptionsDTO(
                exception.getMessage(),
                exception.getStackTrace(),
                CURRENT_TIME
        );
    }
}