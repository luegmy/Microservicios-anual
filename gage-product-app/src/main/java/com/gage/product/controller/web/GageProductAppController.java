package com.gage.product.controller.web;

import com.gage.product.dto.ProductDTO;
import com.gage.product.service.ProductService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
public class GageProductAppController {

    private final ProductService productService;

    GageProductAppController(ProductService productService){
        this.productService=productService;
    }

    @GetMapping(
            value    = "/product/{productId}",
            produces = "application/json")
    ProductDTO getProduct(@PathVariable int productId){
       return productService.getProduct(productId);
    }

    @PostMapping(
            value    = "/product",
            consumes = "application/json",
            produces = "application/json")
    ProductDTO createProduct(@RequestBody ProductDTO body){
        return productService.createProduct(body);
    }

    @DeleteMapping(value = "/product/{productId}")
    void deleteProduct(@PathVariable int productId){
         productService.deleteProduct(productId);
    }
}
