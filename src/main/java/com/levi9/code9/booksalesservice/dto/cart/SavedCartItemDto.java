package com.levi9.code9.booksalesservice.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SavedCartItemDto {
    private Long id;
    private Long bookId;
    private Long quantity;
}
