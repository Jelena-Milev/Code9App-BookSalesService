package com.levi9.code9.booksalesservice.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.levi9.code9.booksalesservice.dto.cart.CartItemDto;
import com.levi9.code9.booksalesservice.dto.cart.SavedCartItemDto;
import com.levi9.code9.booksalesservice.dto.order.OrderDto;
import com.levi9.code9.booksalesservice.exception.ObjectNotFoundException;
import com.levi9.code9.booksalesservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@RequestMapping(path = "orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(path = "", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderDto> save(@RequestBody final List<Long> cartItemsIds) {
        if(cartItemsIds == null || cartItemsIds.isEmpty())
            throw new ObjectNotFoundException("There must be at list one order item");
        final Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final OrderDto orderDto = orderService.save(cartItemsIds, userId);
        return new ResponseEntity(orderDto, HttpStatus.OK);
    }
}
