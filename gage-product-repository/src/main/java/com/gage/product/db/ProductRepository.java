package com.gage.product.db;

import com.gage.product.domain.ProductEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Optional;
@Repository
public interface ProductRepository extends PagingAndSortingRepository<ProductEntity, String> {
    Optional<ProductEntity> findByProductId(int productId);
}
