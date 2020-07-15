package com.levi9.code9.booksalesservice.dto.cart;

import lombok.*;

import javax.validation.constraints.Positive;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CartItemDto {

    @NonNull
    @Positive(message = "Book id must be valid")
    private Long bookId;

    @NonNull
    @Positive(message = "Book quantity must be positive number")
    private Long quantity;
}
