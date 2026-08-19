package com.samba.library.book.service;

import com.samba.library.book.model.BookEntity;
import com.samba.library.book.model.exception.BookCreationException;
import com.samba.library.book.model.exception.BookNotFoundException;
import com.samba.library.book.persistence.BookRepository;
import io.micrometer.common.util.StringUtils;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;
import java.util.Optional;


@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookEntity> getAllBook(){
        return bookRepository.findAll();
    }
    
    public BookEntity getBookById(Long id){
        return bookRepository.findById(id).orElseThrow(()->new BookNotFoundException("Le livre avec l'id " + id +" n'existe pas !"));
    }

    public BookEntity updateBook(Long id, String isbn, String bookName, Integer bookPages, Integer year, String description){
        BookEntity book = getBookById(id);
        if (isbn == null || StringUtils.isBlank(isbn))
        {
            //return "Le bookName ne peut pas etre null ou vide";
            throw new BookCreationException("L'isbn ne peut pas etre null ou vide");
        }
        if (!BookService.isValidIsbn13(isbn)) {
            throw new BookCreationException("L'isbn doit etre valide !");
        }

        if (bookName == null || StringUtils.isBlank(bookName))
        {
            //return "Le bookName ne peut pas etre null ou vide";
            throw new BookCreationException("Le bookName ne peut etre null ou vide");
        }
        if (bookPages ==null || bookPages <=0){
            //return "Le nombre de page ne peut pas etre inférieur ou égal 0 ";
            throw new BookCreationException("Le nombre de page ne peut pas etre inférieur ou égal 0 ");
        }

        if (year ==null || year > Year.now().getValue()){
            //return "Le nombre de page ne peut pas etre inférieur ou égal 0 ";
            throw new BookCreationException("L'année de parution ne peut pas etre postérieur a l'année actuelle ");
        }

        book.setIsbn(isbn);
        book.setName(bookName);
        book.setPages(bookPages);
        book.setYear(year);
        book.setDescription(description);

        bookRepository.save(book);

        return book;
    }
    

    public BookEntity createBook(String isbn, String bookName, Integer bookPages, Integer year, String description) throws BookCreationException {

        if (isbn == null || StringUtils.isBlank(isbn))
        {
            //return "Le bookName ne peut pas etre null ou vide";
            throw new BookCreationException("L'isbn ne peut pas etre null ou vide");
        }
        if (!BookService.isValidIsbn13(isbn)) {
            throw new BookCreationException("L'isbn doit etre valide !");
        }

        if (bookName == null || StringUtils.isBlank(bookName))
        {
            //return "Le bookName ne peut pas etre null ou vide";
            throw new BookCreationException("Le bookName ne peut etre null ou vide");
        }
        if (bookPages ==null || bookPages <=0){
            //return "Le nombre de page ne peut pas etre inférieur ou égal 0 ";
            throw new BookCreationException("Le nombre de page ne peut pas etre inférieur ou égal 0 ");
        }

        if (year ==null || year > Year.now().getValue()){
            //return "Le nombre de page ne peut pas etre inférieur ou égal 0 ";
            throw new BookCreationException("L'année de parution ne peut pas etre postérieur a l'année actuelle ");
        }


        BookEntity existingBook = bookRepository.findByIsbn(isbn);
        //BookEntity existingBook = bookRepository.findByNameAndPages(bookName, bookPages);

        if (existingBook != null){
            throw new BookCreationException("Le livre existe déja !");
        }
        BookEntity newBook = BookEntity.builder()
                .isbn(isbn)
                .name(bookName)
                .pages(bookPages)
                .year(year)
                .description(description)
                .build();

        bookRepository.save(newBook);

        return newBook;

    }

    public static boolean isValidIsbn13(String rawIsbn){
        if (rawIsbn ==null){
            return false;
        }
        //1- netoyage : enlever les espces et tirets
        String isbn = rawIsbn.replaceAll("[\\s-]+","");

        //2- verifier la longueur et tout est bien numérique

        if(!isbn.matches("\\d{13}")){
            return false;
        }

        return true;

        //3- calcuk du check digit

//        int sum = 0;
//        for (int i = 0; i < 12; i++) {
//            int digit = isbn.charAt(i) - '0';
//            // position paires/impaires en base 0
//
//            if (i%2 ==0){
//                //poids 1 pour  d1,d3,d5 ... => i = 0,2,4
//                sum += digit;
//            }else {
//                //poids 3 pour d2, d4, d6 ... => i = 1,3,5
//                sum += 3 * digit;
//            }
//        }
//        int modulo = sum % 10;
//        int expectedCheckDigit = (10-modulo)%10;
//
//        int actualCheckDigit = isbn .charAt(12) - '0';
//
//        return expectedCheckDigit == actualCheckDigit;

    }
}
