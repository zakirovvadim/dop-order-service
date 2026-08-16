package ru.vadim.orderservice.config;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import org.springframework.boot.jackson.JacksonMixin;
import ru.vadim.orderservice.module.product.Product;
import ru.vadim.orderservice.module.product.ProductStatus;
import ru.vadim.orderservice.module.product.ProductStatus.Activee;
import ru.vadim.orderservice.module.product.ProductStatus.Discountinued;

@JsonTypeInfo(
        use = Id.DEDUCTION,
        defaultImpl = ProductStatus.Activee.class
)
@JsonSubTypes({
        @JsonSubTypes.Type(Activee.class),
        @JsonSubTypes.Type(Discountinued.class),
})
@JacksonMixin(ProductStatus.class)
public class ProductStatusMixin {
}
