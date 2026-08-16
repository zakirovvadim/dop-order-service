package ru.vadim.orderservice.service.impl;

import ru.vadim.orderservice.module.order.CreateOrderCommand;
import ru.vadim.orderservice.module.order.OrderRequest;
import ru.vadim.orderservice.module.order.OrderResponse;
import ru.vadim.orderservice.orchestration.OrderOrchestrator;
import ru.vadim.orderservice.orchestration.OrderState;
import ru.vadim.orderservice.orchestration.OrderState.Fulfilled;
import ru.vadim.orderservice.orchestration.OrderState.Placed;
import ru.vadim.orderservice.service.OrderService;
import ru.vadim.orderservice.util.DomainDtoMapper;

public class OrderServiceImpl implements OrderService {

    private final OrderOrchestrator orderOrchestrator;

    public OrderServiceImpl(OrderOrchestrator orderOrchestrator) {
        this.orderOrchestrator = orderOrchestrator;
    }

    @Override
    public OrderResponse placeOrder(OrderRequest request) {
        CreateOrderCommand command = DomainDtoMapper.toCreateOrderCommand(request);
        var placedOrderState = new Placed(command);
        OrderState orderState = this.orderOrchestrator.orchestrate(placedOrderState);
        return switch (orderState) {
            case Fulfilled fulfilled -> DomainDtoMapper.toOrderResponse(fulfilled.order(), fulfilled.invoice(), fulfilled.shipments());
            default -> throw new IllegalStateException("Unexpected value: " + orderState); // should not happen
        };
    }
}
