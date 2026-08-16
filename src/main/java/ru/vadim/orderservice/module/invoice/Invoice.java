package ru.vadim.orderservice.module.invoice;

import java.time.LocalDate;
import java.util.UUID;
import ru.vadim.orderservice.module.common.PriceSummary;

public sealed interface Invoice {

    record Paid(String id,
                UUID orderId,
                String customerId,
                String transactionId,
                PriceSummary priceSummary) implements Invoice {

    }

    record Unpaid(String id,
                  UUID orderId,
                  String customerId,
                  String businessName,
                  String businessTaxId,
                  PriceSummary priceSummary,
                  LocalDate paymentDue) implements Invoice {
    }
}
