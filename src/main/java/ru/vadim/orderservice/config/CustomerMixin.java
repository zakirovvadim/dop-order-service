package ru.vadim.orderservice.config;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import org.springframework.boot.jackson.JacksonMixin;
import ru.vadim.orderservice.module.customer.Customer;

@JsonTypeInfo(
        use = Id.DEDUCTION,
        defaultImpl = Customer.Regular.class
)
@JsonSubTypes({
        @JsonSubTypes.Type(Customer.Regular.class),
        @JsonSubTypes.Type(Customer.Business.class),
})
@JacksonMixin(Customer.class)
public class CustomerMixin {
}
