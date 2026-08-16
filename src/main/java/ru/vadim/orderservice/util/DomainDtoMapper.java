package ru.vadim.orderservice.util;

import java.util.List;
import java.util.Optional;
import ru.vadim.orderservice.module.invoice.Invoice;
import ru.vadim.orderservice.module.invoice.Invoice.Unpaid;
import ru.vadim.orderservice.module.order.CreateOrderCommand;
import ru.vadim.orderservice.module.order.Order;
import ru.vadim.orderservice.module.order.OrderRequest;
import ru.vadim.orderservice.module.order.OrderResponse;
import ru.vadim.orderservice.module.order.OrderResponse.InvoiceDetails;
import ru.vadim.orderservice.module.order.OrderResponse.Product;
import ru.vadim.orderservice.module.product.Product.Bundle;
import ru.vadim.orderservice.module.product.Product.Single;
import ru.vadim.orderservice.module.shipping.Shipment;

public class DomainDtoMapper {

    private static final String COMPLETED = "Completed";
    private static final String PAID = "Paid";
    private static final String UNPAIND = "Unpaid";

    public static CreateOrderCommand toCreateOrderCommand(OrderRequest orderRequest) {
        return CreateOrderCommand.create(
                orderRequest.customerId(),
                orderRequest.productId(),
                orderRequest.quantity()
        );
    }

    public static OrderResponse toOrderResponse(Order order, Invoice invoice, List<Shipment> shipments) {
        return new OrderResponse(
                order.orderId(),
                COMPLETED,
                toProducts(order),
                toInvoiceDetails(invoice),
                shipments
        );
    }

    private static OrderResponse.InvoiceDetails toInvoiceDetails(Invoice invoice) {
        return switch (invoice) {
            case Invoice.Paid paid -> new OrderResponse.InvoiceDetails(paid.id(), PAID, paid.priceSummary(), Optional.empty());
            case Unpaid unpaid -> new InvoiceDetails(unpaid.id(), UNPAIND, unpaid.priceSummary(), Optional.of(unpaid.paymentDue()));
        };
    }

    private static List<OrderResponse.Product> toProducts(Order order) {
        var quantity = order.orderItem().quantity();
        return switch (order.orderItem().product()) {
            case Single single -> List.of(toProduct(single, quantity));
            case Bundle bundle -> bundle.items()
                    .stream()
                    .map(single -> toProduct(single, quantity))
                    .toList();
        };
    }

    private static OrderResponse.Product toProduct(Single single, int quantity) {
        return new Product(
                single.productId(),
                single.name(),
                single.price(),
                quantity
        );
    }
}
