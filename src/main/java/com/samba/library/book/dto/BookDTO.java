package com.samba.library.book.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

public class BookDTO {


    @Data
    @AllArgsConstructor
    @Builder
    public static class PostInput{

        @NotNull @ NotBlank
        String isbn;
        @NotNull @NotBlank
        String bookName ;
        @NotNull
        Integer bookPages;
        @NotNull
        Integer year;
        String description;
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class PostOutput{

        Long id;
        String isbn;
        String bookName ;
        Integer bookPages;
        Integer year;
        String description;
    }


}
