package com.gage.product.service;

import com.gage.product.dto.ProductDTO;
import reactor.core.publisher.Mono;

public interface ProductService {

    Mono<ProductDTO> getProduct(int productId);
    Mono<ProductDTO> createProduct(ProductDTO product);
    Mono<Void> deleteProduct(int productId);

}
