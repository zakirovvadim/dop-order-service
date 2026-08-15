package ru.vadim.orderservice.orchestration;

import java.util.List;
import ru.vadim.orderservice.module.common.PriceSummary;
import ru.vadim.orderservice.module.invoice.Invoice;
import ru.vadim.orderservice.module.order.CreateOrderCommand;
import ru.vadim.orderservice.module.order.Order;
import ru.vadim.orderservice.module.shipping.Shipment;

public sealed interface OrderState {

    record Placed(CreateOrderCommand request) implements OrderState {

    }

    record Validated(Order order) implements OrderState {

    }

    record Priced(Order order,
                  PriceSummary priceSummary) implements OrderState {

    }

    record Invoiced(Order order,
                    Invoice invoice) implements OrderState {

    }

    record Shipped(Order order,
                   Invoice invoice,
                   List<Shipment> shipments) implements OrderState {

    }

    record Fulfilled(Order order,
                     Invoice invoice,
                     List<Shipment> shipments) implements OrderState {

    }
}
