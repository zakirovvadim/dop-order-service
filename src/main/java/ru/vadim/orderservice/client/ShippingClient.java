package ru.vadim.orderservice.client;

import ru.vadim.orderservice.module.shipping.ShippingRequest;
import ru.vadim.orderservice.module.shipping.ShippingResponse;

public interface ShippingClient {

    ShippingResponse schedule(ShippingRequest request);
}
