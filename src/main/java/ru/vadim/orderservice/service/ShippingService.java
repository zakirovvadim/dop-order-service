package ru.vadim.orderservice.service;

import ru.vadim.orderservice.module.order.Order;
import ru.vadim.orderservice.module.shipping.ShippingResponse;

public interface ShippingService {
    ShippingResponse scheduleShipping(Order order);
}
