package com.levi9.code9.booksalesservice.dto.bookService;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class AuthorDto {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
}
