package com.levi9.code9.booksalesservice.dto.cart;

import com.levi9.code9.booksalesservice.dto.bookService.AuthorDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class CartItemInfoDto {
    private Long id;
    private Long bookId;
    private AuthorDto author;
    private String title;
    private BigDecimal price;
    private Long quantity;
}
