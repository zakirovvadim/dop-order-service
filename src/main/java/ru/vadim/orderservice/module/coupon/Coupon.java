package ru.vadim.orderservice.module.coupon;

import java.util.Objects;

public sealed interface Coupon {

    record None() implements Coupon{
        public static final Coupon NONE = new None();
    }

    record Flat(String code,
                Integer discount) implements Coupon {

    }

    record Percentage(String code,
                      Integer percent,
                      Integer maxDiscount) implements Coupon {

        public Percentage {
            maxDiscount = Objects.requireNonNullElse(maxDiscount, Integer.MAX_VALUE);
        }
    }

    static Coupon none() {
        return None.NONE;
    }
}
