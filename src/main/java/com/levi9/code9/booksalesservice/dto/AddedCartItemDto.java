package com.levi9.code9.booksalesservice.dto;

import com.levi9.code9.booksalesservice.model.AuthorEntity;
import com.levi9.code9.booksalesservice.model.GenreEntity;
import lombok.*;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.Set;

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
