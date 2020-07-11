package com.levi9.code9.booksalesservice.mapper;

import com.levi9.code9.booksalesservice.dto.BookDto;
import com.levi9.code9.booksalesservice.dto.GenreDto;
import com.levi9.code9.booksalesservice.model.BookEntity;
import com.levi9.code9.booksalesservice.model.BookGenre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookEntity map(BookDto bookDto);
}
