package com.library.book.services;

import com.library.book.dtos.BookDTO;

import java.util.List;

public interface BookService {
    List<BookDTO> findBooksAvailable();
    List<BookDTO> findAllBooks();
    List<BookDTO> findBooksByName(String bookName);
    List<BookDTO> findBooksByAuthor(String bookAuthor);
    List<BookDTO> findBookById(List<Integer> bookId);
}
