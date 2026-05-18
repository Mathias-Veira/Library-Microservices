package com.library.loan.error;

public class MaxLoansException extends RuntimeException{
    public MaxLoansException(String message) {
        super(message);
    }
}
