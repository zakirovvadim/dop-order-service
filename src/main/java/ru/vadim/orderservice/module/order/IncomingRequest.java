package ru.vadim.orderservice.module.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record IncomingRequest(@NotBlank String customerId,
                              @NotBlank String productId,
                              @Min(1) int quantity) {
}
