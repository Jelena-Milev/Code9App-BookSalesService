package com.levi9.code9.booksalesservice.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.levi9.code9.booksalesservice.dto.bookService.BookCopiesSoldDto;
import com.levi9.code9.booksalesservice.dto.bookService.BookDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "code9-books-service", fallback = BookServiceApiFallback.class)
public interface BookServiceApi {

    @GetMapping(value = "/books-management/books/{id}", produces = APPLICATION_JSON_VALUE)
    BookDto getBook(@PathVariable final Long id);

    @GetMapping(value = "/books-management/books/bulk", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    List<BookDto> getBulkBooks(@RequestBody final List<Long> ids);

    @RequestMapping(method = RequestMethod.PATCH, value = "/books-management/books/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    BookDto updateCopiesSold(@PathVariable final Long id, @RequestBody final BookCopiesSoldDto copiesSold);
}
