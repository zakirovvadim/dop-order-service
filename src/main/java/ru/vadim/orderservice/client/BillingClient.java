package ru.vadim.orderservice.client;

import ru.vadim.orderservice.module.invoice.Invoice;
import ru.vadim.orderservice.module.invoice.InvoiceRequest;

public interface BillingClient {

    Invoice createInvoice(InvoiceRequest invoiceRequest);
}
