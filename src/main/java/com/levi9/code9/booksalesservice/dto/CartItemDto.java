package com.levi9.code9.booksalesservice.dto;

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
