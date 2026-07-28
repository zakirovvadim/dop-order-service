package ru.vadim.orderservice.module.shipping;

import java.time.LocalDate;

public record TrackingDetails(String carrier,
                              String trackingNumber,
                              LocalDate estimateDeliveryDate) {
}
