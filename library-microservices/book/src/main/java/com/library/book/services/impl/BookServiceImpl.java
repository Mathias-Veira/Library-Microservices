package com.library.book.services.impl;

import com.library.book.dtos.BookDTO;
import com.library.book.mappers.BookMapper;
import com.library.book.repositories.BookRepository;
import com.library.book.services.BookService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    public BookServiceImpl(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }
    @Override
    public List<BookDTO> findBooksAvailable() {
        return BookMapper.changeToListDTO(bookRepository.findBooksAvailable());
    }

    @Override
    public List<BookDTO> findAllBooks() {
        return BookMapper.changeToListDTO(bookRepository.findAll());
    }

    @Override
    public List<BookDTO> findBooksByName(String bookName) {
        return BookMapper.changeToListDTO(bookRepository.findBooksByName(bookName));
    }

    @Override
    public List<BookDTO> findBooksByAuthor(String bookAuthor) {
        return BookMapper.changeToListDTO(bookRepository.findBooksByAuthor(bookAuthor));
    }

    @Override
    public List<BookDTO> findBookById(List<Integer> bookId) {
        return BookMapper.changeToListDTO(bookRepository.findBooksById(bookId));
    }
}
