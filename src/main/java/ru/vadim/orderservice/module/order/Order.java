package ru.vadim.orderservice.module.order;

import java.time.LocalDateTime;
import java.util.UUID;
import ru.vadim.orderservice.module.coupon.Coupon;
import ru.vadim.orderservice.module.customer.Customer;

public record Order(UUID orderId,
                    Customer customer,
                    OrderItem orderItem,
                    Coupon coupon,
                    LocalDateTime createdAt) {
}
