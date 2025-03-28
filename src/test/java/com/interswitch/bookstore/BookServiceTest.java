package com.interswitch.bookstore;

import com.interswitch.bookstore.exceptions.BookNotFoundException;
import com.interswitch.bookstore.model.Book;
import com.interswitch.bookstore.model.Genre;
import com.interswitch.bookstore.repository.BookRepository;
import com.interswitch.bookstore.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book book1;
    private Book book2;

    @BeforeEach
    void setUp() {
        book1 = new Book(null, "Things Fall Apart", Genre.FICTION, "571-3-4451-4622-0", "Chinue Achebe", 1958, 2000);
        book2 = new Book(null, "Shoe Dog", Genre.THRILLER, "978-1-4711-4672-5", "Phil Knight", 2016, 1500);
    }

    @Test
    void testSearchByTitle() {
        when(bookRepository.findByTitleContainingIgnoreCase("Things Fall Apart")).thenReturn(Arrays.asList(book1));

        List<Book> books = bookService.searchByTitle("Things Fall Apart");

        assertEquals(1, books.size());
        assertEquals("Things Fall Apart", books.get(0).getTitle());
        verify(bookRepository, times(1)).findByTitleContainingIgnoreCase("Things Fall Apart");
    }

    @Test
    void testSearchByTitle_NotFound() {
        when(bookRepository.findByTitleContainingIgnoreCase("Unknown")).thenReturn(Collections.emptyList());

        assertThrows(BookNotFoundException.class, () -> bookService.searchByTitle("Unknown"));
    }

    @Test
    void testSearchByAuthor() {
        when(bookRepository.findByAuthorContainingIgnoreCase("Phil Knight")).thenReturn(Arrays.asList(book2));

        List<Book> books = bookService.searchByAuthor("Phil Knight");

        assertEquals(1, books.size());
        assertEquals("Shoe Dog", books.get(0).getTitle());
        verify(bookRepository, times(1)).findByAuthorContainingIgnoreCase("Phil Knight");
    }

    @Test
    void testSearchByAuthor_NotFound() {
        when(bookRepository.findByAuthorContainingIgnoreCase("Unknown Author")).thenReturn(Collections.emptyList());

        assertThrows(BookNotFoundException.class, () -> bookService.searchByAuthor("Unknown Author"));
    }

    @Test
    void testSearchByYear() {
        when(bookRepository.findByPublicationYear(2016)).thenReturn(Arrays.asList(book2));

        List<Book> books = bookService.searchByYear(2016);

        assertEquals(1, books.size());
        assertEquals(2016, books.get(0).getPublicationYear());
        verify(bookRepository, times(1)).findByPublicationYear(2016);
    }

    @Test
    void testSearchByYear_NotFound() {
        when(bookRepository.findByPublicationYear(1900)).thenReturn(Collections.emptyList());

        assertThrows(BookNotFoundException.class, () -> bookService.searchByYear(1900));
    }

    @Test
    void testSearchByGenre() {
        when(bookRepository.findByGenre(Genre.FICTION)).thenReturn(Arrays.asList(book1));

        List<Book> books = bookService.searchByGenre(Genre.FICTION);

        assertEquals(1, books.size());
        assertEquals(Genre.FICTION, books.get(0).getGenre());
        verify(bookRepository, times(1)).findByGenre(Genre.FICTION);
    }

    @Test
    void testSearchByGenre_NotFound() {
        when(bookRepository.findByGenre(Genre.MYSTERY)).thenReturn(Collections.emptyList());

        assertThrows(BookNotFoundException.class, () -> bookService.searchByGenre(Genre.MYSTERY));
    }

    @Test
    void testSearchBooks_NoFilter_ThrowsException() {
        assertThrows(BookNotFoundException.class, () -> bookService.searchBooks(null, null, null, null));
    }
}
