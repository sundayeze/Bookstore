package com.interswitch.bookstore.controller;

import com.interswitch.bookstore.exceptions.BookNotFoundException;
import com.interswitch.bookstore.model.Book;
import com.interswitch.bookstore.model.Genre;
import com.interswitch.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

//    private BookService bookService;
//
//    public BookController(BookService bookService) {
//        this.bookService = bookService;
//    }

    @GetMapping("/search")
    public ResponseEntity<?> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String genre) {

        Genre formatGenre = null;
        if (genre != null) {
            try {
                formatGenre = Genre.valueOf(genre.toUpperCase().trim());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body("Invalid genre: " + genre);
            }
        }

        try {
            List<Book> books = bookService.searchBooks(title, author, year, formatGenre);
            return ResponseEntity.ok(books);
        } catch (BookNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllBooks(){
        try {
            List<Book> books = bookService.getAllBooks();
            return ResponseEntity.ok(books);
        } catch (BookNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
