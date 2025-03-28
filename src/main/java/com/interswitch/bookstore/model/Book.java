package com.interswitch.bookstore.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@Table(name = "books")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Title cannot be blank")
    @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "Title must contain only letters and numbers")
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Genre cannot be null")
    private Genre genre;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "ISBN cannot be blank")
    @Pattern(regexp = "^[0-9-]+$", message = "ISBN must contain only numbers and dashes (-)")
    private String isbn;

    @Column(nullable = false)
    @NotBlank(message = "Author cannot be blank")
    private String author;

    @Column(nullable = false)
    @NotNull(message = "Year cannot be null")
    private int publicationYear;

    @Column(nullable = false)
    @Positive
    @NotNull(message = "Price cannot be null")
    private double price;


    public Book() {
    }

    public Book(Long id, String title, Genre genre, String isbn, String author, int publicationYear, double price) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.isbn = isbn;
        this.author = author;
        this.publicationYear = publicationYear;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

