package ru.vadim.orderservice.client.impl;

import java.util.Collections;
import org.springframework.web.client.RestClient;
import ru.vadim.orderservice.client.ShippingClient;
import ru.vadim.orderservice.module.shipping.ShippingRequest;
import ru.vadim.orderservice.module.shipping.ShippingResponse;

public class ShippingServiceClient extends AbstractServiceClient implements ShippingClient {

    private final RestClient restClient;

    public ShippingServiceClient(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public ShippingResponse schedule(ShippingRequest request) {
        return this.excecuteRequest(
                () -> this.restClient.post()
                        .uri("/schedule")
                        .body(request)
                        .retrieve()
                        .body(ShippingResponse.class),
                Collections.emptyMap()
        );
    }

    @Override
    protected String getServiceName() {
        return "shipping-service";
    }
}
