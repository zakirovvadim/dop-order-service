package ru.vadim.orderservice.service.impl;

import java.util.List;
import ru.vadim.orderservice.client.ShippingClient;
import ru.vadim.orderservice.module.order.Order;
import ru.vadim.orderservice.module.product.Product.Bundle;
import ru.vadim.orderservice.module.product.Product.Single;
import ru.vadim.orderservice.module.shipping.Recipient;
import ru.vadim.orderservice.module.shipping.ShipmentItem;
import ru.vadim.orderservice.module.shipping.ShippingRequest;
import ru.vadim.orderservice.module.shipping.ShippingResponse;
import ru.vadim.orderservice.service.ShippingService;

public class ShippingServiceImpl implements ShippingService {

    private final ShippingClient shippingClient;

    public ShippingServiceImpl(ShippingClient shippingClient) {
        this.shippingClient = shippingClient;
    }

    @Override
    public ShippingResponse scheduleShipping(Order order) {
        var request = this.toShippingRequest(order);
        return this.shippingClient.schedule(request);
    }

    private ShippingRequest toShippingRequest(Order order) {
        var recipient = new Recipient(order.customer().name(), order.customer().address());
        var quantity = order.orderItem().quantity();
        var items = switch (order.orderItem().product()) {
            case Single single -> List.of(new ShipmentItem(single.productId(), quantity));
            case Bundle bundle -> bundle.items()
                    .stream()
                    .map(Single::productId)
                    .map(id -> new ShipmentItem(id, quantity))
                    .toList();
        };
        return new ShippingRequest(order.orderId(), recipient, items);
    }
}
