package com.library.book.dtos;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@NoArgsConstructor
public class BookEventDTO {
    private String eventId;
    private int userId;
    private int bookId;
    private LocalDateTime timestamp;

    public BookEventDTO(int userId, int bookId) {
        this.eventId = UUID.randomUUID().toString();
        this.userId = userId;
        this.bookId = bookId;
        this.timestamp = LocalDateTime.now();
    }
}
