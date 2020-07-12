package com.levi9.code9.booksalesservice.service.impl;

import com.levi9.code9.booksalesservice.controller.BookServiceApi;
import com.levi9.code9.booksalesservice.dto.bookService.BookDto;
import com.levi9.code9.booksalesservice.dto.cart.CartItemDto;
import com.levi9.code9.booksalesservice.dto.order.OrderDto;
import com.levi9.code9.booksalesservice.mapper.BookMapper;
import com.levi9.code9.booksalesservice.mapper.OrderMapper;
import com.levi9.code9.booksalesservice.model.OrderEntity;
import com.levi9.code9.booksalesservice.model.OrderItemEntity;
import com.levi9.code9.booksalesservice.model.book.BookEntity;
import com.levi9.code9.booksalesservice.repository.OrderRepository;
import com.levi9.code9.booksalesservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final BookServiceApi bookServiceApi;
    private final BookMapper bookMapper;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, BookServiceApi bookServiceApi, BookMapper bookMapper, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.bookServiceApi = bookServiceApi;
        this.bookMapper = bookMapper;
        this.orderMapper = orderMapper;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public OrderDto save(List<CartItemDto> itemsDtos, Long userId) {
        OrderEntity order = new OrderEntity();
        BigDecimal totalPrice = new BigDecimal(0);
        final List<OrderItemEntity> items = new ArrayList<>(itemsDtos.size());
        for (CartItemDto itemDto : itemsDtos) {
            final BookEntity bookEntity = fetchBook(itemDto.getBookId());
            if(bookEntity == null){
                //throw exception
            }
            final Long quantity = itemDto.getQuantity();
            OrderItemEntity orderItemEntity = new OrderItemEntity(bookEntity, quantity);
            items.add(orderItemEntity);

            final Long itemPrice = quantity.longValue()*bookEntity.getPrice().longValue();
            totalPrice = totalPrice.add(new BigDecimal(itemPrice.longValue()));
        }
        order.setOrderIdentifier("ABCDEF123");
        order.setDate(LocalDate.now());
        order.setUserId(userId);
        order.setTotalPrice(totalPrice);
        OrderEntity savedOrder = orderRepository.save(order);
        for (OrderItemEntity item : items) {
            item.setOrder(savedOrder);
        }
        savedOrder.setOrderItems(items);
        savedOrder = orderRepository.save(order);
        final OrderDto orderDto = orderMapper.mapToDto(savedOrder);
        return orderDto;
    }

    private BookEntity fetchBook(Long id){
        final BookDto bookDto = bookServiceApi.getBook(id);
        final BookEntity bookEntity = bookMapper.map(bookDto);
        return bookEntity;
    }
}
