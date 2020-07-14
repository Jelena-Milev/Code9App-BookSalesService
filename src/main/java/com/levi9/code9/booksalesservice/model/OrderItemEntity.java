package com.levi9.code9.booksalesservice.model;

import com.levi9.code9.booksalesservice.dto.bookService.BookDto;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "OrderItem")
@Table(name = "order_item")
public class OrderItemEntity {

    @Id
    @SequenceGenerator(name = "seq_order_item_ids", sequenceName = "seq_order_item_ids", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_order_item_ids")
    private Long id;

    private Long bookId;

    private Long quantity;

    @ManyToOne()
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private OrderEntity order;

    @Transient
    private BookDto book;

    public OrderItemEntity(Long bookId, Long quantity, BookDto book) {
        this.bookId = bookId;
        this.quantity = quantity;
        this.book = book;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItemEntity)) return false;
        OrderItemEntity that = (OrderItemEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(order, that.order);
    }

    @Override
    public int hashCode() {
        return 52;
    }


}
