package ru.vadim.orderservice.client;

import ru.vadim.orderservice.module.customer.Customer;

public interface CustomerClient {

    Customer getCustomer(String customerId);
}
