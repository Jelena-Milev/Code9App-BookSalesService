package com.levi9.code9.booksalesservice.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.levi9.code9.booksalesservice.dto.BookCopiesSoldDto;
import com.levi9.code9.booksalesservice.dto.BookDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "code9-books-service", fallback = BookServiceApiFallback.class)
public interface BookServiceApi {

    @GetMapping(value = "/books-management/books/{id}", produces = APPLICATION_JSON_VALUE)
    BookDto getBook(@PathVariable final Long id);

    @RequestMapping(method = RequestMethod.PATCH, value = "/books-management/books/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    BookDto updateCopiesSold(@PathVariable final Long id, @RequestBody final BookCopiesSoldDto copiesSold);
}
