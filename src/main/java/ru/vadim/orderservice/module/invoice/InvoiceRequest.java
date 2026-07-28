package ru.vadim.orderservice.module.invoice;

import java.util.UUID;
import ru.vadim.orderservice.module.common.PriceSummary;

public sealed interface InvoiceRequest {

    record Paid(UUID orderId,
                String customerId,
                String transactionId,
                PriceSummary priceSummary) implements InvoiceRequest {

    }

    record Unpaid(UUID orderId,
                  String customerId,
                  String businessName,
                  String businessTaxId,
                  PriceSummary priceSummary) implements InvoiceRequest {
    }
}
