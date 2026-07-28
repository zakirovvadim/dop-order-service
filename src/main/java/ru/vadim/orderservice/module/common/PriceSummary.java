package ru.vadim.orderservice.module.common;

public record PriceSummary(double subTotal,
                           double discountApplied,
                           double taxAmount,
                           double finalAmount) {
}
