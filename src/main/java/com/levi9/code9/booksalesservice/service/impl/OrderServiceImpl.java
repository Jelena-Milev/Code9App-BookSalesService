package com.levi9.code9.booksalesservice.service.impl;

import com.levi9.code9.booksalesservice.controller.BookServiceApi;
import com.levi9.code9.booksalesservice.dto.bookService.BookDto;
import com.levi9.code9.booksalesservice.dto.cart.CartItemDto;
import com.levi9.code9.booksalesservice.dto.order.OrderDto;
import com.levi9.code9.booksalesservice.mapper.OrderMapper;
import com.levi9.code9.booksalesservice.model.CartItemEntity;
import com.levi9.code9.booksalesservice.model.OrderEntity;
import com.levi9.code9.booksalesservice.model.OrderItemEntity;
import com.levi9.code9.booksalesservice.repository.CartItemRepository;
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
    private final CartItemRepository cartItemRepository;
    private final BookServiceApi bookServiceApi;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, CartItemRepository cartItemRepository, BookServiceApi bookServiceApi, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.cartItemRepository = cartItemRepository;
        this.bookServiceApi = bookServiceApi;
        this.orderMapper = orderMapper;
    }

    @Override
    @Transactional
    public OrderDto save(List<Long> cartItemsIds, Long userId) throws Exception {
        final List<CartItemEntity> cartItemEntities = getCartItems(cartItemsIds);
        final OrderEntity orderToSave = createOrder(cartItemEntities, userId);
        final OrderEntity savedOrder = orderRepository.save(orderToSave);
        updateCopiesSold(savedOrder);
        cartItemRepository.deleteAll(cartItemEntities);
        return orderMapper.mapToDto(savedOrder);
    }

    private List<CartItemEntity> getCartItems(List<Long> cartItemsIds) {
        List<CartItemEntity> cartItemEntities = new ArrayList<>(cartItemsIds.size());
        for (Long cartItemsId : cartItemsIds) {
            final CartItemEntity cartItemEntity = cartItemRepository.findById(cartItemsId).get();
            //ovde moze exception da baci ako ne postoji stavka sa tim id-em
            cartItemEntities.add(cartItemEntity);
        }
        return cartItemEntities;
    }

    private void updateCopiesSold(OrderEntity savedOrder) {
        List<CartItemDto> bookCopiesSoldDtos = new ArrayList<>(savedOrder.getOrderItems().size());
        savedOrder.getOrderItems().forEach(orderItem -> {
            final Long bookId = orderItem.getBookId();
            final Long quantity = orderItem.getQuantity();
            bookCopiesSoldDtos.add(new CartItemDto(bookId, quantity));
        });
        bookServiceApi.updateCopiesSold(bookCopiesSoldDtos);
    }

    @Transactional
    public OrderEntity createOrder(List<CartItemEntity> cartItemscartItemEntities, Long userId) throws Exception {
        final List<OrderItemEntity> orderItems = createOrderItems(cartItemscartItemEntities);
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

    private List<OrderItemEntity> createOrderItems(List<CartItemEntity> cartItemEntities) throws Exception{
        final List<OrderItemEntity> generatedItems = new ArrayList<>(cartItemEntities.size());
        final List<BookDto> books = fetchBooks(cartItemEntities);
        for (BookDto book : books) {
            if (book == null) {
                continue;
                //uradi nesto;
            }
            final CartItemEntity cartItem = cartItemEntities.stream().filter(item -> item.getBookId() == book.getId()).findFirst().get();
            if (cartItem.getQuantity() > book.getQuantityOnStock()) {
                //uradi nesto
                throw new Exception("Too much books");
            }
            final OrderItemEntity itemEntity = new OrderItemEntity(book.getId(), cartItem.getQuantity(), book);
            generatedItems.add(itemEntity);
        }
        return generatedItems;
    }


    private List<BookDto> fetchBooks(List<CartItemEntity> cartItemEntities) {
        final List<Long> booksIds = new ArrayList<>(cartItemEntities.size());
        cartItemEntities.forEach(cartItemEntity -> booksIds.add(cartItemEntity.getBookId()));
        final List<BookDto> bookDtos = bookServiceApi.getBulkBooks(booksIds);
        return bookDtos;
    }
}
