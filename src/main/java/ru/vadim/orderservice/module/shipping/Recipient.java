package ru.vadim.orderservice.module.shipping;

import ru.vadim.orderservice.module.common.Address;

public record Recipient(String name,
                        Address address) {
}
