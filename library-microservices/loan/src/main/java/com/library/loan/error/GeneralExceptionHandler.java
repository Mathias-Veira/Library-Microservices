package com.library.loan.error;

import com.library.loan.dtos.ErrorDTO;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GeneralExceptionHandler {
    @ExceptionHandler(IdNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorDTO> IdNotFoundException(IdNotFoundException exception){
        ErrorDTO error = new ErrorDTO(HttpStatus.NOT_FOUND,exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    @ExceptionHandler(NotRefreshTokenException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorDTO> NotRefreshTokenException(NotRefreshTokenException exception){
        ErrorDTO error = new ErrorDTO(HttpStatus.NOT_FOUND,exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(FeignException.NotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorDTO> FeignException(FeignException.NotFound exception){
        ErrorDTO error = new ErrorDTO(HttpStatus.NOT_FOUND,"User or Book not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
