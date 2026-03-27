package com.library.book.error;

public class NotRefreshTokenException extends RuntimeException{
    public NotRefreshTokenException(String message) {
        super(message);
    }
}
