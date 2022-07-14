package com.gage.product.consumer;

import com.gage.product.dto.ProductDTO;
import com.gage.product.event.Event;
import com.gage.product.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

@EnableBinding(Sink.class)
public class MessageProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(MessageProcessor.class);

    private final ProductService productService;

    public MessageProcessor(ProductService productService) {
        this.productService = productService;
    }

    @StreamListener(target = Sink.INPUT)
    void process(Event<Integer,ProductDTO>event){
        LOG.info("Process message created at {}...", event.getEventCreatedAt());
        ProductDTO product = event.getData();
        LOG.info("Create product with ID: {}", product.getProductId());
        productService.createProduct(product);
    }
}
