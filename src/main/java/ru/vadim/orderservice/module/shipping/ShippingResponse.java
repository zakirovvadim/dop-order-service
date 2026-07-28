package ru.vadim.orderservice.module.shipping;

import java.util.List;
import java.util.UUID;

public record ShippingResponse(UUID orderId,
                               List<Shipment> shipments) {
}
