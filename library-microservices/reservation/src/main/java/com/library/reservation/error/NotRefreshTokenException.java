package com.library.reservation.error;

public class NotRefreshTokenException extends RuntimeException{
    public NotRefreshTokenException(String message) {
        super(message);
    }
}
