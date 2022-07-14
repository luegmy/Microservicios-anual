package com.gage.product.service;

import com.gage.product.db.ProductMapper;
import com.gage.product.db.ProductRepository;
import com.gage.product.domain.ProductEntity;
import com.gage.product.dto.ProductDTO;
import com.gage.product.exception.InvalidInputException;
import com.gage.product.exception.NotFoundException;
import com.gage.product.http.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ServiceUtil serviceUtil;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ServiceUtil serviceUtil, ProductRepository productRepository,
                              ProductMapper productMapper) {
        this.serviceUtil = serviceUtil;
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public Mono<ProductDTO> getProduct(int productId) {
        LOG.debug("/product return the found product for productId={}", productId);
        if (productId < 1) throw new InvalidInputException("Invalid productId: " + productId);
        return productRepository.findByProductId(productId)
                .switchIfEmpty(Mono.error(new NotFoundException("No product found for productId: " + productId)))
                .map(productMapper::entityToDto);
    }

    @Override
    public Mono<ProductDTO> createProduct(ProductDTO productDTO) {
        LOG.debug("createProduct: entity created for productId: {}", productDTO.getProductId());
        ProductEntity entity = productMapper.dtoToEntity(productDTO);
        return productRepository.save(entity)
                .onErrorMap(
                        DuplicateKeyException.class,
                        ex -> new InvalidInputException("Duplicate key, Product Id: " + productDTO.getProductId()))
                .map(productMapper::entityToDto);

    }

    @Override
    public Mono<Void> deleteProduct(int productId) {
        LOG.debug("deleteProduct: tries to delete an entity with productId: {}", productId);
        return productRepository.findByProductId(productId).map(productRepository::delete).then();
    }
}