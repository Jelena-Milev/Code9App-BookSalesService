package com.levi9.code9.booksalesservice.dto.cart;

import com.levi9.code9.booksalesservice.model.book.AuthorEntity;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AddedCartItemDto {

    private Long bookId;
    private String title;
    private BigDecimal price;
    private AuthorEntity author;

    private Long quantity;

}
