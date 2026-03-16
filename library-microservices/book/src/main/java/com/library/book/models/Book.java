package com.library.book.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "Book")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Book  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int bookId;
    @Column(name = "book_name")
    private String bookName;
    @Column(name = "book_author")
    private String bookAuthor;
    @Column(name = "book_pages")
    private int bookPages;
    @Column(name = "book_synopsis")
    private String bookSynopsis;
    private boolean available;
}
