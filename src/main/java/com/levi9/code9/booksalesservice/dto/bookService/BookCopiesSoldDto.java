package com.levi9.code9.booksalesservice.dto.bookService;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BookCopiesSoldDto {
    private Long bookId;
    private Long copiesSold;
}
