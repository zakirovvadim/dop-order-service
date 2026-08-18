package ru.vadim.orderservice.client;

import ru.vadim.orderservice.module.coupon.Coupon;

public interface CouponClient {

    Coupon getCoupon(String code);
}
