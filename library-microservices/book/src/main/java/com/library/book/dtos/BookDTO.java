package com.library.book.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookDTO {
    private int bookId;
    private String bookName;
    private String bookAuthor;
    private int bookPages;
    private String bookSynopsis;
    private boolean available;
}
