package et.com.gebeya.inventory_management.service;

import et.com.gebeya.inventory_management.Models.Product;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class VendorServiceClient {
    private final WebClient webClient;

    public VendorServiceClient( @Qualifier("vendorWebClient") WebClient webClient) {
        this.webClient = webClient;
    }
    public Flux<Product> getProductsById(List<Long> productIds) {
        return this.webClient.post()
                .uri("/api/v1/products/byIds")
                .bodyValue(productIds)
                .retrieve()
                .bodyToFlux(Product.class);
    }
}
