package com.levi9.code9.booksalesservice.service.impl;

import com.levi9.code9.booksalesservice.controller.BookServiceApi;
import com.levi9.code9.booksalesservice.dto.bookService.BookCopiesSoldDto;
import com.levi9.code9.booksalesservice.dto.bookService.BookDto;
import com.levi9.code9.booksalesservice.dto.cart.CartItemDto;
import com.levi9.code9.booksalesservice.dto.order.OrderDto;
import com.levi9.code9.booksalesservice.mapper.OrderMapper;
import com.levi9.code9.booksalesservice.model.OrderEntity;
import com.levi9.code9.booksalesservice.model.OrderItemEntity;
import com.levi9.code9.booksalesservice.repository.OrderRepository;
import com.levi9.code9.booksalesservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final BookServiceApi bookServiceApi;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, BookServiceApi bookServiceApi, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.bookServiceApi = bookServiceApi;
        this.orderMapper = orderMapper;
    }

    @Override
    @Transactional
    public OrderDto save(List<CartItemDto> cartItemsDtos, Long userId) throws Exception {
        final OrderEntity orderToSave = createOrder(cartItemsDtos, userId);
        final OrderEntity savedOrder = orderRepository.save(orderToSave);

        updateCopiesSold(savedOrder);
        return orderMapper.mapToDto(savedOrder);
    }

    private void updateCopiesSold(OrderEntity savedOrder) {
        List<BookCopiesSoldDto> bookCopiesSoldDtos = new ArrayList<>(savedOrder.getOrderItems().size());
        savedOrder.getOrderItems().forEach(orderItem -> {
            final Long bookId = orderItem.getBookId();
            final Long quantity = orderItem.getQuantity();
            bookCopiesSoldDtos.add(new BookCopiesSoldDto(bookId, quantity));
        });
        bookServiceApi.updateCopiesSold(bookCopiesSoldDtos);
    }

    @Transactional
    public OrderEntity createOrder(List<CartItemDto> cartItemsDtos, Long userId) throws Exception {
        final List<OrderItemEntity> orderItems = createOrderItems(cartItemsDtos);
        final BigDecimal totalPrice = calculateTotalPrice(orderItems);
        final String orderIdentifier = getOrderIdentifier();
        OrderEntity order = new OrderEntity(orderIdentifier, userId, LocalDate.now(), totalPrice);
        for (OrderItemEntity item : orderItems) {
            order.addOrderItem(item);
        }
        return order;
    }

    private String getOrderIdentifier() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    private BigDecimal calculateTotalPrice(List<OrderItemEntity> orderItems) {
        BigDecimal totalPrice = new BigDecimal(0);
        for (OrderItemEntity orderItem : orderItems) {
            final long quantity = orderItem.getQuantity().longValue();
            final long bookPrice = orderItem.getBook().getPrice().longValue();
            final Long itemPrice = quantity * bookPrice;
            totalPrice = totalPrice.add(new BigDecimal(itemPrice.longValue()));
        }
        return totalPrice;
    }

    private List<OrderItemEntity> createOrderItems(List<CartItemDto> itemsDtos) throws Exception{
        final List<OrderItemEntity> generatedItems = new ArrayList<>(itemsDtos.size());
        final List<BookDto> books = fetchBooks(itemsDtos);
        for (BookDto book : books) {
            if (book == null) {
                continue;
                //uradi nesto;
            }
            final CartItemDto itemDto = itemsDtos.stream().filter(item -> item.getBookId() == book.getId()).findFirst().get();
            if (itemDto.getQuantity() > book.getQuantityOnStock()) {
                //uradi nesto
                throw new Exception("Too much books");
            }
            final OrderItemEntity itemEntity = new OrderItemEntity(book.getId(), itemDto.getQuantity(), book);
            generatedItems.add(itemEntity);
        }
        return generatedItems;
    }


    private List<BookDto> fetchBooks(List<CartItemDto> itemsDtos) {
        final List<Long> ids = new ArrayList<>(itemsDtos.size());
        itemsDtos.forEach(itemDto -> ids.add(itemDto.getBookId()));
        final List<BookDto> bookDtos = bookServiceApi.getBulkBooks(ids);
        return bookDtos;
    }
}
