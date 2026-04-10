package com.library.book.services;

import com.library.book.dtos.BookDTO;
import com.library.book.dtos.BookEventDTO;

import java.util.List;

public interface BookService {

    List<BookDTO> findAllBooks();
    List<BookDTO> findBooksByName(String bookName);
    List<BookDTO> findBooksByAuthor(String bookAuthor);
    List<BookDTO> findBookById(List<Integer> bookId);
    BookDTO addBookStock(BookEventDTO bookEventDTO);
    void removeBookStock(BookEventDTO bookEventDTO);
}
