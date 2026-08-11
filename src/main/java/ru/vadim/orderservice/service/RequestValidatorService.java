package ru.vadim.orderservice.service;

import ru.vadim.orderservice.module.order.CreateOrderCommand;
import ru.vadim.orderservice.module.order.Order;

public interface RequestValidatorService {

    Order validate(CreateOrderCommand request);
}
