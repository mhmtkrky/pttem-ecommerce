package com.pttem.ecommerce.productmanagement.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pttem.ecommerce.productmanagement.entity.ProductEntity;
import com.pttem.ecommerce.productmanagement.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CountDownLatch;

@Component
public class Receiver {

    private final CountDownLatch latch = new CountDownLatch(1);
    private final ProductRepository repository;

    @Autowired
    public Receiver(ProductRepository repository) {
        this.repository = repository;
    }

    public void receiveMessage(String productUUIDListJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List list = objectMapper.readValue(productUUIDListJson, List.class);
                    list.forEach(x-> {
                        repository.findById(Long.parseLong(x.toString()))
                                .ifPresent(y -> System.out.println(y.getName() + " sold"));
                    } );

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}