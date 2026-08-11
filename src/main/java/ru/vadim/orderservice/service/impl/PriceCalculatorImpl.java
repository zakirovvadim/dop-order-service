package ru.vadim.orderservice.service.impl;

import ru.vadim.orderservice.module.common.PriceSummary;
import ru.vadim.orderservice.module.order.Order;
import ru.vadim.orderservice.module.product.Product.Bundle;
import ru.vadim.orderservice.module.product.Product.Single;
import ru.vadim.orderservice.service.PriceCalculator;

public class PriceCalculatorImpl implements PriceCalculator {
    @Override
    public PriceSummary calculate(Order order) {
        var state = order.customer().address().state();
        var quantity = order.orderItem().quantity();
        return switch (order.orderItem().product()) {
            case Single single -> this.toPriceSummary(single.price(), single.price(), quantity, state);
            case Bundle bundle -> this.toPriceSummary(bundle.originalPrice(), bundle.discountPrice(), quantity, state);
        };
    }

    private PriceSummary toPriceSummary(double unitPrice, double discountedPrice, int quantity, String state) {
        var subTotal = unitPrice * quantity;
        var discounterAmount = discountedPrice * quantity;
        var tax = discounterAmount * gettAxRate(state);
        return new PriceSummary(
                subTotal,
                subTotal - discounterAmount,
                tax,
                discounterAmount + tax
        );
    }

    private double gettAxRate(String state) {
        return switch (state) {
            case "MI" -> 0.06;
            case "WA" -> 0.09;
            case "FL" -> 0.07;
            case "TX", "NY", "CA", "IL", "AZ" -> 0.08;
            default -> 0.05;
        };
    }
}
