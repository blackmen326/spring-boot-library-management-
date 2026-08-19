package com.samba.library.book.controllers;

import com.samba.library.book.dto.BookDTO;
import com.samba.library.book.model.BookEntity;
import com.samba.library.book.model.exception.BookCreationException;
import com.samba.library.book.service.BookService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/book")
public class BookRestController {

    private final BookService bookService;

    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookDTO.PostOutput getBookById(@PathVariable Long id){
        BookEntity book = bookService.getBookById(id);
        return BookDTO.PostOutput.builder()
                .id(book.getId())
                .isbn(book.getIsbn())
                .bookName(book.getName())
                .bookPages(book.getPages())
                .year(book.getYear())
                .description(book.getDescription())
                .build();
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDTO.PostOutput> get() {
        List<BookEntity> books = bookService.getAllBook();
        return books.stream().map(
                book -> BookDTO.PostOutput.builder()
                        .id(book.getId())
                        .isbn(book.getIsbn())
                        .bookName(book.getName())
                        .bookPages(book.getPages())
                        .year(book.getYear())
                        .description(book.getDescription())
                        .build()
        ).toList();
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO.PostOutput post(@Valid @RequestBody BookDTO.PostInput input) throws BookCreationException {
        BookEntity newBook = bookService.createBook(input.getIsbn(), input.getBookName(), input.getBookPages(), input.getYear(), input.getDescription());


        return BookDTO.PostOutput.builder()
                .id(newBook.getId())
                .isbn(newBook.getIsbn())
                .bookName(newBook.getName())
                .bookPages(newBook.getPages())
                .year(newBook.getYear())
                .description(newBook.getDescription())
                .build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public BookDTO.PostOutput updateBook(@PathVariable Long id, @ Valid @RequestBody BookDTO.PutInput input) throws BookCreationException{
        BookEntity updatedBook = bookService.updateBook(
                id,
                input.getIsbn(),
                input.getBookName(),
                input.getBookPages(),
                input.getYear(),
                input.getDescription()
        );

        return BookDTO.PostOutput.builder()
                .id(updatedBook.getId())
                .isbn(updatedBook.getIsbn())
                .bookName(updatedBook.getName())
                .bookPages(updatedBook.getPages())
                .year(updatedBook.getYear())
                .description(updatedBook.getDescription())
                .build();
    }








//    @GetMapping("/book/{id}")
//    public BookDTO.PostOutput put(@Valid @RequestBody BookDTO.PostInput) throws BookCreationException{
//
//    }
}


