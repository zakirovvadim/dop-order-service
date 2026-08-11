package ru.vadim.orderservice.service;

import ru.vadim.orderservice.module.common.PriceSummary;
import ru.vadim.orderservice.module.order.Order;

public interface PriceCalculator {

    PriceSummary calculate(Order order);
}
