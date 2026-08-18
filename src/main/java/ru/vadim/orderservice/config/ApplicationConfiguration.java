package ru.vadim.orderservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import ru.vadim.orderservice.client.BillingClient;
import ru.vadim.orderservice.client.CouponClient;
import ru.vadim.orderservice.client.CustomerClient;
import ru.vadim.orderservice.client.PaymentClient;
import ru.vadim.orderservice.client.ProductClient;
import ru.vadim.orderservice.client.ShippingClient;
import ru.vadim.orderservice.client.impl.BillingServiceClient;
import ru.vadim.orderservice.client.impl.CouponServiceClient;
import ru.vadim.orderservice.client.impl.CustomerServiceClient;
import ru.vadim.orderservice.client.impl.PaymentServiceClient;
import ru.vadim.orderservice.client.impl.ProductServiceClient;
import ru.vadim.orderservice.client.impl.ShippingServiceClient;
import ru.vadim.orderservice.orchestration.OrderOrchestrator;
import ru.vadim.orderservice.orchestration.impl.OrderOrchestratorImpl;
import ru.vadim.orderservice.service.OrderService;
import ru.vadim.orderservice.service.PaymentBillingService;
import ru.vadim.orderservice.service.PriceCalculator;
import ru.vadim.orderservice.service.RequestValidatorService;
import ru.vadim.orderservice.service.ShippingService;
import ru.vadim.orderservice.service.impl.OrderServiceImpl;
import ru.vadim.orderservice.service.impl.PaymentBillingServiceImpl;
import ru.vadim.orderservice.service.impl.PriceCalculatorImpl;
import ru.vadim.orderservice.service.impl.RequestValidatorServiceImpl;
import ru.vadim.orderservice.service.impl.ShippingServiceImpl;

@Configuration
public class ApplicationConfiguration {

    private static final Logger log = LoggerFactory.getLogger(ApplicationConfiguration.class);
    private final RestClient.Builder builder;

    public ApplicationConfiguration(RestClient.Builder builder) {
        this.builder = builder.requestInterceptor(new LoggingInterceptor());
    }

    @Bean
    public ProductClient productClient(@Value("${product.service.url}") String url) {
        return new ProductServiceClient(buildRestClient(url));
    }

    @Bean
    public CustomerClient customerClient(@Value("${customer.service.url}") String url) {
        return new CustomerServiceClient(buildRestClient(url));
    }

    @Bean
    public PaymentClient paymentClient(@Value("${payment.service.url}") String url) {
        return new PaymentServiceClient(buildRestClient(url));
    }

    @Bean
    public BillingClient billingClient(@Value("${billing.service.url}") String url) {
        return new BillingServiceClient(buildRestClient(url));
    }

    @Bean
    public ShippingClient shippingClient(@Value("${shipping.service.url}") String url) {
        return new ShippingServiceClient(buildRestClient(url));
    }

    @Bean
    public CouponClient couponClient(@Value("${coupon.service.url}") String url) {
        return new CouponServiceClient(buildRestClient(url));
    }

    @Bean
    public RequestValidatorService requestValidatorService(ProductClient productClient, CustomerClient customerClient, CouponClient couponClient) {
        return new RequestValidatorServiceImpl(productClient, customerClient, couponClient);
    }

    @Bean
    public PriceCalculator priceCalculator() {
        return new PriceCalculatorImpl();
    }

    @Bean
    public PaymentBillingService paymentBillingService(PaymentClient paymentClient, BillingClient billingClient) {
        return new PaymentBillingServiceImpl(paymentClient, billingClient);
    }

    @Bean
    public ShippingService shippingService(ShippingClient shippingClient) {
        return new ShippingServiceImpl(shippingClient);
    }

    @Bean
    public OrderOrchestrator orderOrchestrator(RequestValidatorService validatorService,
                                               PriceCalculator priceCalculator,
                                               PaymentBillingService paymentBillingService,
                                               ShippingService shippingService) {

        return new OrderOrchestratorImpl(validatorService, priceCalculator, paymentBillingService, shippingService);
    }

    @Bean
    public OrderService orderService(OrderOrchestrator orderOrchestrator) {
        return new OrderServiceImpl(orderOrchestrator);
    }

    private RestClient buildRestClient(String baseUrl) {
        log.info("base url: {}", baseUrl);
        return this.builder.baseUrl(baseUrl)
                .build();
    }
}
