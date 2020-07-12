package com.levi9.code9.booksalesservice.model;

import com.levi9.code9.booksalesservice.model.book.BookEntity;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "OrderItem")
@Table(name = "order_item")
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private BookEntity book;

    private Long quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private OrderEntity order;

    public OrderItemEntity(BookEntity book, Long quantity) {
        this.book = book;
        this.quantity = quantity;
    }
}
