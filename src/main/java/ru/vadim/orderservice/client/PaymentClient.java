package ru.vadim.orderservice.client;

import ru.vadim.orderservice.module.payment.PaymentRequest;
import ru.vadim.orderservice.module.payment.PaymentStatus;

public interface PaymentClient {

    PaymentStatus process(PaymentRequest request);
}
