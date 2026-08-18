package ru.vadim.orderservice.config;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import org.springframework.boot.jackson.JacksonMixin;
import ru.vadim.orderservice.module.coupon.Coupon;

@JsonTypeInfo(
        use = Id.DEDUCTION,
        defaultImpl = Coupon.None.class
)
@JsonSubTypes({
        @JsonSubTypes.Type(Coupon.None.class),
        @JsonSubTypes.Type(Coupon.Flat.class),
        @JsonSubTypes.Type(Coupon.Percentage.class)
})
@JacksonMixin(Coupon.class)
public class CouponMixin {
}
