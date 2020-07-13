package com.levi9.code9.booksalesservice.dto.cart;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CartItemDto {
    private Long bookId;
    private Long quantity;
}
