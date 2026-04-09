package com.library.loan.error;

public class ActiveLoanException extends RuntimeException{
    public ActiveLoanException(String message) {
        super(message);
    }
}
