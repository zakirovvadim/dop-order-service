package ru.vadim.orderservice.client.impl;

import java.util.Map;
import java.util.function.Supplier;
import org.springframework.web.client.RestClient;
import ru.vadim.orderservice.client.CouponClient;
import ru.vadim.orderservice.module.coupon.Coupon;

public class CouponServiceClient extends AbstractServiceClient implements CouponClient {

    private final RestClient restClient;

    public CouponServiceClient(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public Coupon getCoupon(String code) {
        var erroMap = Map.<Integer, Supplier<Coupon>>of(
                404, Coupon::none
        );
        return this.excecuteRequest(() -> this.restClient.get()
                        .uri("/{code}", code)
                        .retrieve()
                        .body(Coupon.class),
                erroMap);
    }

    @Override
    protected String getServiceName() {
        return "coupon-service";
    }
}
