package com.library.loan.error;

public class NotRefreshTokenException extends RuntimeException{
    public NotRefreshTokenException(String message) {
        super(message);
    }
}
