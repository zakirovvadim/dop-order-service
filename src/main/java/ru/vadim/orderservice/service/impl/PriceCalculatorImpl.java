package ru.vadim.orderservice.service.impl;

import ru.vadim.orderservice.module.common.PriceSummary;
import ru.vadim.orderservice.module.coupon.Coupon;
import ru.vadim.orderservice.module.coupon.Coupon.Flat;
import ru.vadim.orderservice.module.coupon.Coupon.None;
import ru.vadim.orderservice.module.coupon.Coupon.Percentage;
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
            case Single single -> this.toPriceSummary(single.price(), single.price(), quantity, state, order.coupon());
            case Bundle bundle -> this.toPriceSummary(bundle.originalPrice(), bundle.discountedPrice(), quantity, state, order.coupon());
        };
    }

    private PriceSummary toPriceSummary(double unitPrice, double discountedPrice, int quantity, String state, Coupon coupon) {
        var subTotal = unitPrice * quantity;
        var discounterAmount = this.applyCoupon(coupon, discountedPrice * quantity);
        var tax = discounterAmount * gettAxRate(state);
        return new PriceSummary(
                subTotal,
                subTotal - discounterAmount,
                tax,
                discounterAmount + tax
        );
    }

    private double applyCoupon(Coupon coupon, double amount) {
        var payableAmount = switch (coupon) {
            case None _ -> amount;
            case Flat flat -> amount - flat.discount();
            case Percentage percentage -> this.applyPercentageCoupon(percentage, amount);
        };
        return Math.max(5, payableAmount);
    }

    private double applyPercentageCoupon(Percentage percentage, double amount) {
        var discounted = (percentage.percent() / 100d) * amount;
        var maxDiscount = Math.min(discounted, percentage.maxDiscount());
        return amount - maxDiscount;
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
