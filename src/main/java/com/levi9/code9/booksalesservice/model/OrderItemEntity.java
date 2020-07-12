package com.levi9.code9.booksalesservice.model;

import com.levi9.code9.booksalesservice.model.book.BookEntity;
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

    private Long id;

    private BookEntity book;

    private Long quantity;

    private OrderEntity order;

    public OrderItemEntity(BookEntity book, Long quantity) {
        this.book = book;
        this.quantity = quantity;
    }

    @Id
    @SequenceGenerator(name = "seq_order_item_ids", sequenceName = "seq_order_item_ids", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_order_item_ids")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    public BookEntity getBook() {
        return book;
    }

    public void setBook(BookEntity book) {
        this.book = book;
    }

    @ManyToOne()
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
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
