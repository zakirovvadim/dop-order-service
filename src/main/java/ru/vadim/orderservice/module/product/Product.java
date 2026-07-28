package ru.vadim.orderservice.module.product;

import java.util.List;

public sealed interface Product {

    String productId();
    String name();

    record Single(String productId,
                  String name,
                  Double price) implements Product {

    }

    record Bundle(String productId,
                  String name,
                  double originalPrice,
                  double discountPrice,
                  List<Single> items) implements Product {

    }
}
