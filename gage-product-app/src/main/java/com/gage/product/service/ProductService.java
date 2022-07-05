package com.gage.product.service;

import com.gage.product.dto.ProductDTO;
import reactor.core.publisher.Mono;

public interface ProductService {

    ProductDTO getProduct(int productId);
    ProductDTO createProduct(ProductDTO product);
    void deleteProduct(int productId);

}
