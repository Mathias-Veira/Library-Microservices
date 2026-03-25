package com.library.user.error;

public class NotRefreshTokenException extends RuntimeException{
    public NotRefreshTokenException(String message) {
        super(message);
    }
}
