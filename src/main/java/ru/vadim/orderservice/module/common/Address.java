package ru.vadim.orderservice.module.common;

public record Address(String street,
                      String city,
                      String state,
                      String zipCode) {
}
