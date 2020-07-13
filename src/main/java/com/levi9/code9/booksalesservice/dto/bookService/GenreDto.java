package com.levi9.code9.booksalesservice.dto.bookService;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class GenreDto {
    private Long id;
    private String name;
}
