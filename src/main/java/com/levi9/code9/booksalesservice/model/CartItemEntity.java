package com.levi9.code9.booksalesservice.model;

import com.levi9.code9.booksalesservice.model.book.BookEntity;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "CartItem")
@Table(name = "cart_item")
public class CartItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userId;

    @ManyToOne
    @JoinColumn(name="book_id", referencedColumnName = "id")
    private BookEntity book;
    private Long quantity;
}
