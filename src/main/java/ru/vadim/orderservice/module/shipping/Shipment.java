package ru.vadim.orderservice.module.shipping;

public record Shipment(String shipmentId,
                       String productId,
                       int quantity,
                       String shippingAddress,
                       TrackingDetails trackingDetails) {
}
