package ru.vadim.orderservice.module.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import ru.vadim.orderservice.module.common.PriceSummary;
import ru.vadim.orderservice.module.shipping.Shipment;

public record OrderResponse(UUID orderId,
                            String status,
                            List<Product> products,
                            InvoiceDetails invoiceDetails,
                            List<Shipment> shipments) {

    public record Product(String id,
                          String name,
                          double unitPrice,
                          int quantity) {

    }

    @JsonInclude(Include.NON_ABSENT)
    public record InvoiceDetails(String invoiceId,
                                 String paymentStatus,
                                 PriceSummary priceSummary,
                                 Optional<LocalDate> paymentDue) {

    }
}
