package com.lcmartins.infrastructure.api;

import com.lcmartins.application.order.create.BaseCreateOrderUseCase;
import com.lcmartins.application.order.create.CreateOrderCommand;
import com.lcmartins.application.order.create.OrderOutput;
import com.lcmartins.infrastructure.orders.dto.OrderAPIInput;
import com.lcmartins.infrastructure.orders.dto.OrderItemAPIConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("orders")
@RestController
public class OrderController {
    BaseCreateOrderUseCase createOrderUseCase;

    public OrderController(BaseCreateOrderUseCase createOrderUseCase) {
        this.createOrderUseCase = createOrderUseCase;
    }

    @PostMapping
    public ResponseEntity<OrderOutput> create(@RequestBody OrderAPIInput input) {
        return ResponseEntity.status(HttpStatus.OK).body(
                createOrderUseCase.execute(
                        CreateOrderCommand.with(input.customerId(),
                                OrderItemAPIConverter.convert.apply(input.items())
                        )
                )
        );
    }
}
