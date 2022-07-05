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
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ServiceUtil serviceUtil;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ServiceUtil serviceUtil, ProductRepository productRepository, ProductMapper productMapper) {
        this.serviceUtil = serviceUtil;
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductDTO getProduct(int productId) {
        LOG.debug("/product return the found product for productId={}", productId);
        if (productId < 1) throw new InvalidInputException("Invalid productId: " + productId);

        ProductEntity entity = productRepository.findByProductId(productId)
                .orElseThrow(() -> new NotFoundException("No product found for productId: " + productId));

        ProductDTO response = productMapper.entityToDto(entity);
        response.setServiceAddress(serviceUtil.getServiceAddress());

        LOG.debug("getProduct: found productId: {}", response.getProductId());

        return response;
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        try {
            ProductEntity entity = productMapper.dtoToEntity(productDTO);
            ProductEntity newEntity = productRepository.save(entity);

            LOG.debug("createProduct: entity created for productId: {}", productDTO.getProductId());
            return productMapper.entityToDto(newEntity);

        } catch (DuplicateKeyException dke) {
            throw new InvalidInputException("Duplicate key, Product Id: " + productDTO.getProductId());
        }
    }

    @Override
    public void deleteProduct(int productId) {
        LOG.debug("deleteProduct: tries to delete an entity with productId: {}", productId);
        productRepository.findByProductId(productId).ifPresent(productRepository::delete);


    }
}