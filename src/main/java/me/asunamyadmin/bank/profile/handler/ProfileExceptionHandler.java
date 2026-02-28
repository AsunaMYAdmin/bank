package me.asunamyadmin.bank.profile.handler;

import me.asunamyadmin.bank.global.ExceptionsDTO;
import me.asunamyadmin.bank.profile.exception.AlreadyHaveThisStatusException;
import me.asunamyadmin.bank.profile.exception.ProfileNotFoundException;
import me.asunamyadmin.bank.profile.exception.UsernameAlreadyTakenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ProfileExceptionHandler {
    @ExceptionHandler(ProfileNotFoundException.class)
    public ResponseEntity<ExceptionsDTO> handleException(ProfileNotFoundException pex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ExceptionsDTO(
                        pex.getMessage(),
                        pex.getStackTrace(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(UsernameAlreadyTakenException.class)
    public ResponseEntity<ExceptionsDTO> handleException(UsernameAlreadyTakenException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ExceptionsDTO(
                        ex.getMessage(),
                        ex.getStackTrace(),
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(AlreadyHaveThisStatusException.class)
    public ResponseEntity<ExceptionsDTO> handleException(AlreadyHaveThisStatusException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ExceptionsDTO(
                ex.getMessage(),
                ex.getStackTrace(),
                LocalDateTime.now()
        ));
    }
}
