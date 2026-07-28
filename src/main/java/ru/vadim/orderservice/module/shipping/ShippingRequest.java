package ru.vadim.orderservice.module.shipping;

import java.util.List;
import java.util.UUID;

public record ShippingRequest(UUID orderId,
                              Recipient recipient,
                              List<ShipmentItem> items) {
}
