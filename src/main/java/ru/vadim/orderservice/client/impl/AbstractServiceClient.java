package ru.vadim.orderservice.client.impl;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpStatusCodeException;
import ru.vadim.orderservice.exception.ApplicationExceptions;

abstract class AbstractServiceClient {

    private static final Logger log = LoggerFactory.getLogger(AbstractServiceClient.class);

    protected abstract String getServiceName();

    protected <T> T excecuteRequest(Supplier<T> supplier, Map<Integer, Supplier<T>> errorMap) {
        try {
            var t = supplier.get();
            log.info("response: {}", t);
            return t;
        } catch (HttpStatusCodeException ex) {
            log.error("error response from {}", this.getServiceName(), ex);
            return Optional.ofNullable(errorMap.get(ex.getStatusCode().value()))
                    .map(Supplier::get)
                    .orElseGet(() -> ApplicationExceptions.remoteServiceError(this.getServiceName(), ex.getMessage()));
        }
    }
}
