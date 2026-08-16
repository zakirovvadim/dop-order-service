package ru.vadim.orderservice.controller.advice;

import java.util.function.Consumer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.vadim.orderservice.exception.ApplicationError;
import ru.vadim.orderservice.exception.ApplicationException;
import ru.vadim.orderservice.exception.DomainError;
import ru.vadim.orderservice.exception.DomainError.EntityNotFound;
import ru.vadim.orderservice.exception.DomainError.PaymentDeclined;
import ru.vadim.orderservice.exception.DomainError.ProductDiscontinued;
import ru.vadim.orderservice.exception.SystemError.RemoteServiceError;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ProblemDetail handleException(ApplicationException ex) {
        return switch (ex.getApplicationError()) {
            case DomainError.EntityNotFound error -> this.toProblemDetail(error);
            case DomainError.PaymentDeclined error -> this.toProblemDetail(error);
            case DomainError.ProductDiscontinued error -> this.toProblemDetail(error);
            case RemoteServiceError error -> this.toProblemDetail(error);
        };
    }

    private ProblemDetail toProblemDetail(EntityNotFound error) {
        return this.build(HttpStatus.BAD_REQUEST, error, problemDetail -> {
            problemDetail.setTitle("Not found");
            problemDetail.setDetail("Unable to find requested entity %s for the given id %s".formatted(error.entity(), error.id()));
        });
    }

    private ProblemDetail toProblemDetail(PaymentDeclined error) {
        return this.build(HttpStatus.PAYMENT_REQUIRED, error, problemDetail -> {
            problemDetail.setTitle("Payment required");
            problemDetail.setDetail("Payment for the order was declined. Please update your payment information");
        });
    }

    private ProblemDetail toProblemDetail(ProductDiscontinued error) {
        return this.build(HttpStatus.BAD_REQUEST, error, problemDetail -> {
            problemDetail.setTitle("Product discontinued");
            problemDetail.setDetail("The product is discontinued. Check out our top-selling alternatives in the same category");
        });
    }

    private ProblemDetail toProblemDetail(RemoteServiceError error) {
        return this.build(HttpStatus.SERVICE_UNAVAILABLE, error, problemDetail -> {
            problemDetail.setTitle("Service unavailable");
            problemDetail.setDetail("Unable to filfill the order. Please try later");
        });
    }

    private ProblemDetail build(HttpStatus status, ApplicationError error, Consumer<ProblemDetail> consumer) {
        var problem = ProblemDetail.forStatus(status);
        problem.setProperty("additionalInformation", error);
        consumer.accept(problem);
        return problem;
    }
}
