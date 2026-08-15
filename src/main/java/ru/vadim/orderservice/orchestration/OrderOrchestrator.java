package ru.vadim.orderservice.orchestration;

import ru.vadim.orderservice.orchestration.OrderState.Fulfilled;
import ru.vadim.orderservice.orchestration.OrderState.Invoiced;
import ru.vadim.orderservice.orchestration.OrderState.Placed;
import ru.vadim.orderservice.orchestration.OrderState.Priced;
import ru.vadim.orderservice.orchestration.OrderState.Shipped;
import ru.vadim.orderservice.orchestration.OrderState.Validated;

public interface OrderOrchestrator {

    default OrderState orchestrate(OrderState orderState) {
        return switch (orderState) {
            case Placed state -> this.orchestrate(this.handle(state));
            case Validated state -> this.orchestrate(this.handle(state));
            case Priced state -> this.orchestrate(this.handle(state));
            case Invoiced state -> this.orchestrate(this.handle(state));
            case Shipped state -> this.orchestrate(this.handle(state));
            case Fulfilled state -> state;
        };
    }

    OrderState handle(OrderState.Placed placed);
    OrderState handle(OrderState.Validated validated);
    OrderState handle(OrderState.Priced priced);
    OrderState handle(OrderState.Invoiced invoiced);
    OrderState handle(OrderState.Shipped shipped);
}
