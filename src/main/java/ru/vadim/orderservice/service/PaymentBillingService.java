package ru.vadim.orderservice.service;

import ru.vadim.orderservice.module.common.PriceSummary;
import ru.vadim.orderservice.module.invoice.Invoice;
import ru.vadim.orderservice.module.order.Order;

public interface PaymentBillingService {
    Invoice processPayment(Order order, PriceSummary priceSummary);
}
