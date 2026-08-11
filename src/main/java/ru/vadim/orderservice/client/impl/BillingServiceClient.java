package ru.vadim.orderservice.client.impl;

import java.util.Collections;
import org.springframework.web.client.RestClient;
import ru.vadim.orderservice.client.BillingClient;
import ru.vadim.orderservice.module.invoice.Invoice;
import ru.vadim.orderservice.module.invoice.InvoiceRequest;

public class BillingServiceClient extends AbstractServiceClient implements BillingClient {

    private final RestClient restClient;

    public BillingServiceClient(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public Invoice createInvoice(InvoiceRequest request) {
        return switch (request) {
            case InvoiceRequest.Paid _ -> this.executeRequest("/invoices/paid", request);
            case InvoiceRequest.Unpaid _ -> this.executeRequest("/invoices/unpaid", request);
        };
    }

    private Invoice executeRequest(String path, InvoiceRequest request) {
        return this.excecuteRequest(
                () ->this.restClient.post()
                        .uri(path)
                        .body(request)
                        .retrieve()
                        .body(Invoice.class),
                Collections.emptyMap()
        );
    }

    @Override
    protected String getServiceName() {
        return "billing-service";
    }
}
