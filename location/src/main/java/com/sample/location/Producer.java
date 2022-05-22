package com.sample.location;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import example.avro.ShoppingCartStatus;

@Service
public class Producer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);

    @Autowired
    private Processor processor;

    public void produceLocationStatus(String cartId,String status) {

        LOGGER.info("Inside Location.Producer.produceLocationStatus");

        // creating ShoppingCartStatus details
        ShoppingCartStatus shoppingCartStatus = new ShoppingCartStatus(cartId,status);
        Message<ShoppingCartStatus> message = MessageBuilder.withPayload(shoppingCartStatus).build();
        processor.output().send(message);
    }
}