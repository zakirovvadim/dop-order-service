package ru.vadim.orderservice.exception;

import ru.vadim.orderservice.exception.DomainError.Entity;
import ru.vadim.orderservice.module.payment.PaymentStatus;
import ru.vadim.orderservice.module.product.ProductStatus;

public class ApplicationExceptions {

    public static <T> T customerNotFound(String id) {
        var error = new DomainError.EntityNotFound(Entity.CUSTOMER, id);
        throw new ApplicationException(error);
    }

    public static <T> T productNotFound(String id) {
        var error = new DomainError.EntityNotFound(Entity.PRODUCT, id);
        throw new ApplicationException(error);
    }

    public static <T> T discontinuedNotFound(ProductStatus.Discountinued discountinued) {
        var error = new DomainError.ProductDiscontinued(discountinued.productId(), discountinued.recommendedProducts());
        throw new ApplicationException(error);
    }

    public static <T> T declinedPayment(PaymentStatus.Declined declined) {
        var error = new DomainError.PaymentDeclined(declined.orderId(), declined.amount());
        throw new ApplicationException(error);
    }

    public static <T> T remoteServiceError(String serviceName, String message) {
        var error = new SystemError.RemoteServiceError(serviceName, message);
        throw new ApplicationException(error);
    }
}
