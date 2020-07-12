package com.levi9.code9.booksalesservice.dto.bookService;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class BookDto {

    private Long id;
    private String title;
    private BigDecimal price;
    private Long quantityOnStock;
    private boolean onStock;
    private Long soldCopiesNumber;

    private AuthorDto author;
    private List<GenreDto> genres;
}
