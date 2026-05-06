package com.library.book.controllers;

import com.library.book.dtos.BookDTO;
import com.library.book.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<BookDTO>> findAllBooks(){
        return new ResponseEntity<>(bookService.findAllBooks(), HttpStatus.OK);
    }

    @GetMapping("/book_by_name")
    public ResponseEntity<List<BookDTO>> findBooksByName(@RequestParam String bookName){
        return new ResponseEntity<>(bookService.findBooksByName(bookName), HttpStatus.OK);
    }
    @GetMapping("/book_by_author")
    public ResponseEntity<List<BookDTO>> findBooksByAuthor(@RequestParam String bookAuthor){
        return new ResponseEntity<>(bookService.findBooksByAuthor(bookAuthor), HttpStatus.OK);
    }
    @GetMapping("/book_by_id")
    public ResponseEntity<List<BookDTO>> findBookById(@RequestParam List<Integer> id){
        return new ResponseEntity<>(bookService.findBookById(id), HttpStatus.OK);
    }
}
