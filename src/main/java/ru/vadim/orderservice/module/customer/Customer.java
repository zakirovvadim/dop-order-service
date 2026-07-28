package ru.vadim.orderservice.module.customer;

import ru.vadim.orderservice.module.common.Address;

public sealed interface Customer {

    String id();
    String name();
    Address address();

    record Regular(String id,
                   String name,
                   Address address) implements Customer {}

    record Business(String id,
                    String name,
                    String taxId,
                    Address address) {

    }
}
