package com.levi9.code9.booksalesservice.dto.cart;

import lombok.*;

import javax.validation.constraints.Positive;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CartItemQuantityDto {
    @NonNull
    @Positive(message = "Book quantity must be positive number")
    private Long newQuantity;
}
