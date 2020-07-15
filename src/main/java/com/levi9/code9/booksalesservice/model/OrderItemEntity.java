package com.levi9.code9.booksalesservice.model;

import com.levi9.code9.booksalesservice.dto.bookService.BookDto;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "OrderItem")
@Table(name = "order_item")
public class OrderItemEntity {

    @Id
    @SequenceGenerator(name = "seq_order_item_ids", sequenceName = "seq_order_item_ids", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_order_item_ids")
    @EqualsAndHashCode.Include
    private Long id;

    private Long bookId;

    private Long quantity;

    @ManyToOne()
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @EqualsAndHashCode.Include
    private OrderEntity order;

    @Transient
    private BookDto book;

    public OrderItemEntity(Long bookId, Long quantity, BookDto book) {
        this.bookId = bookId;
        this.quantity = quantity;
        this.book = book;
    }
}
