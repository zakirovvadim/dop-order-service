package ru.vadim.orderservice.client.impl;

import java.util.Map;
import java.util.function.Supplier;
import org.springframework.web.client.RestClient;
import ru.vadim.orderservice.client.CustomerClient;
import ru.vadim.orderservice.exception.ApplicationExceptions;
import ru.vadim.orderservice.module.customer.Customer;

public class CustomerServiceClient extends AbstractServiceClient implements CustomerClient {

    private final RestClient restClient;

    public CustomerServiceClient(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public Customer getCustomer(String customerId) {
        var errorMap = Map.<Integer, Supplier<Customer>>of(404, () -> ApplicationExceptions.customerNotFound(customerId));
        return this.excecuteRequest(
                () -> this.restClient.get()
                        .uri("/{customerId}", customerId)
                        .retrieve()
                        .body(Customer.class),
                errorMap
        );
    }

    @Override
    protected String getServiceName() {
        return "customer-service";
    }
}
