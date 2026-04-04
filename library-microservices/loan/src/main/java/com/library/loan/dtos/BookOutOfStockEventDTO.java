package com.library.loan.dtos;


import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
public class BookOutOfStockEventDTO {
    private String eventId;
    private int userId;
    private int bookId;
    private LocalDateTime timestamp;

    public BookOutOfStockEventDTO(int userId, int bookId) {
        this.eventId = UUID.randomUUID().toString();
        this.userId = userId;
        this.bookId = bookId;
        this.timestamp = LocalDateTime.now();
    }
}
