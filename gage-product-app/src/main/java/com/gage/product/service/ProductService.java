package com.gage.product.service;

import com.gage.product.dto.ProductDTO;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

public interface ProductService {

    Mono<ProductDTO> getProduct(int productId);
    Mono<ProductDTO> createProduct(@RequestBody ProductDTO product);
    Mono<Void> deleteProduct(int productId);

}
