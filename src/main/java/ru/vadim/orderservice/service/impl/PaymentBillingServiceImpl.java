package ru.vadim.orderservice.service.impl;

import ru.vadim.orderservice.client.BillingClient;
import ru.vadim.orderservice.client.PaymentClient;
import ru.vadim.orderservice.exception.ApplicationExceptions;
import ru.vadim.orderservice.module.common.PriceSummary;
import ru.vadim.orderservice.module.customer.Customer.Business;
import ru.vadim.orderservice.module.customer.Customer.Regular;
import ru.vadim.orderservice.module.invoice.Invoice;
import ru.vadim.orderservice.module.invoice.InvoiceRequest;
import ru.vadim.orderservice.module.order.Order;
import ru.vadim.orderservice.module.payment.PaymentRequest;
import ru.vadim.orderservice.module.payment.PaymentStatus;
import ru.vadim.orderservice.module.payment.PaymentStatus.Declined;
import ru.vadim.orderservice.module.payment.PaymentStatus.Processed;
import ru.vadim.orderservice.service.PaymentBillingService;

public class PaymentBillingServiceImpl implements PaymentBillingService {

    private final PaymentClient paymentClient;
    private final BillingClient billingClient;

    public PaymentBillingServiceImpl(PaymentClient paymentClient, BillingClient billingClient) {
        this.paymentClient = paymentClient;
        this.billingClient = billingClient;
    }

    @Override
    public Invoice processPayment(Order order, PriceSummary priceSummary) {
        var paymentRequest = new PaymentRequest(order.customer().id(), order.orderId(), priceSummary.finalAmount());
        var paymentStatus = this.paymentClient.process(paymentRequest);
        return switch (paymentStatus) {
            case PaymentStatus.Processed processed -> this.toPaidInvoice(order, priceSummary, processed);
            case Declined declined -> this.toUnpaidInvoice(order, priceSummary, declined);
        };
    }

    private Invoice toPaidInvoice(Order order, PriceSummary priceSummary, Processed processed) {
        var request = new InvoiceRequest.Paid(order.orderId(), order.customer().id(), processed.transactionId(), priceSummary);
        return this.billingClient.createInvoice(request);
    }

    private Invoice toUnpaidInvoice(Order order, PriceSummary priceSummary, Declined declined) {
        return switch (order.customer()) {
            case Regular _ -> ApplicationExceptions.declinedPayment(declined);
            case Business business -> this.toUnpaidInvoice(order, priceSummary, business);
        };
    }

    private Invoice toUnpaidInvoice(Order order, PriceSummary priceSummary, Business business) {
        var request = new InvoiceRequest.Unpaid(
                order.orderId(),
                business.id(),
                business.name(),
                business.taxId(),
                priceSummary
        );
        return this.billingClient.createInvoice(request);
    }
}