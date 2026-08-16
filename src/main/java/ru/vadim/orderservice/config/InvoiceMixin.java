package ru.vadim.orderservice.config;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import org.springframework.boot.jackson.JacksonMixin;
import ru.vadim.orderservice.module.invoice.Invoice;

@JsonTypeInfo(
        use = Id.DEDUCTION,
        defaultImpl = Invoice.Paid.class
)
@JsonSubTypes({
        @JsonSubTypes.Type(Invoice.Paid.class),
        @JsonSubTypes.Type(Invoice.Unpaid.class),
})
@JacksonMixin(Invoice.class)
public class InvoiceMixin {
}
