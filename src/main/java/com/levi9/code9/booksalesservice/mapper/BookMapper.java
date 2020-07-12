package com.levi9.code9.booksalesservice.mapper;

import com.levi9.code9.booksalesservice.dto.bookService.BookDto;
import com.levi9.code9.booksalesservice.model.book.BookEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookEntity map(BookDto bookDto);
}
