package ru.vadim.orderservice.client.impl;

import java.util.Map;
import java.util.function.Supplier;
import org.springframework.web.client.RestClient;
import ru.vadim.orderservice.client.PaymentClient;
import ru.vadim.orderservice.module.payment.PaymentRequest;
import ru.vadim.orderservice.module.payment.PaymentStatus;
import ru.vadim.orderservice.module.payment.PaymentStatus.Declined;

public class PaymentServiceClient extends AbstractServiceClient implements PaymentClient {

   private final RestClient restClient;

    public PaymentServiceClient(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public PaymentStatus process(PaymentRequest request) {
        var errorMap = Map.<Integer, Supplier<PaymentStatus>>of(
                402, () -> new Declined(request.orderId(), request.amount())
        );
        return this.excecuteRequest(
                () -> this.restClient.post()
                        .uri("/process")
                        .body(request)
                        .retrieve()
                        .body(PaymentStatus.Processed.class),
                errorMap
        );
    }

    @Override
    protected String getServiceName() {
        return "payment-service";
    }
}
