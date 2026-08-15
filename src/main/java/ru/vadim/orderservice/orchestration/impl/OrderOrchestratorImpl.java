package ru.vadim.orderservice.orchestration.impl;

import ru.vadim.orderservice.module.common.PriceSummary;
import ru.vadim.orderservice.module.invoice.Invoice;
import ru.vadim.orderservice.module.shipping.ShippingResponse;
import ru.vadim.orderservice.orchestration.OrderOrchestrator;
import ru.vadim.orderservice.orchestration.OrderState;
import ru.vadim.orderservice.orchestration.OrderState.Invoiced;
import ru.vadim.orderservice.orchestration.OrderState.Placed;
import ru.vadim.orderservice.orchestration.OrderState.Priced;
import ru.vadim.orderservice.orchestration.OrderState.Shipped;
import ru.vadim.orderservice.orchestration.OrderState.Validated;
import ru.vadim.orderservice.service.PaymentBillingService;
import ru.vadim.orderservice.service.PriceCalculator;
import ru.vadim.orderservice.service.RequestValidatorService;
import ru.vadim.orderservice.service.ShippingService;

public class OrderOrchestratorImpl implements OrderOrchestrator {

    private final RequestValidatorService validatorService;
    private final PriceCalculator priceCalculator;
    private final PaymentBillingService paymentBillingService;
    private final ShippingService shippingService;

    public OrderOrchestratorImpl(RequestValidatorService validatorService, PriceCalculator priceCalculator, PaymentBillingService paymentBillingService, ShippingService shippingService) {
        this.validatorService = validatorService;
        this.priceCalculator = priceCalculator;
        this.paymentBillingService = paymentBillingService;
        this.shippingService = shippingService;
    }

    @Override
    public OrderState handle(Placed placed) {
        var order = this.validatorService.validate(placed.request());
        return new OrderState.Validated(order);
    }

    @Override
    public OrderState handle(Validated validated) {
        PriceSummary priceSummary = this.priceCalculator.calculate(validated.order());
        return new OrderState.Priced(validated.order(), priceSummary);
    }

    @Override
    public OrderState handle(Priced priced) {
        Invoice invoice = this.paymentBillingService.processPayment(priced.order(), priced.priceSummary());
        return new OrderState.Invoiced(priced.order(), invoice);
    }

    @Override
    public OrderState handle(Invoiced invoiced) {
        ShippingResponse shippingResponse = shippingService.scheduleShipping(invoiced.order());
        return new OrderState.Shipped(invoiced.order(), invoiced.invoice(), shippingResponse.shipments());
    }

    @Override
    public OrderState handle(Shipped shipped) {
        return new OrderState.Fulfilled(shipped.order(), shipped.invoice(), shipped.shipments());
    }
}