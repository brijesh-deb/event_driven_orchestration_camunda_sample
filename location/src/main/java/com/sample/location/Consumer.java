package com.sample.location;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import example.avro.ShippingAddress;

@Service
public class Consumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);

    @Autowired
    private Producer producer;

    // Consume ShippingAddress from Kafka topic
    @StreamListener(Processor.INPUT)
    public void consumeShippingDetails(ShippingAddress address) {
        LOGGER.info("Inside Location.Consumer.consumeShippingDetails Cart Id :"+address.getCardId());
        LOGGER.info("Inside Location.Consumer.consumeShippingDetails Street :"+address.getStreet());
        LOGGER.info("Inside Location.Consumer.consumeShippingDetails State:"+address.getState());
        LOGGER.info("Inside Location.Consumer.consumeShippingDetails Pincode :"+address.getPostCode());

        // Add business logic to validate

        // Set status based on address validation outcome; publish to Kafka
        producer.produceLocationStatus(address.getCardId().toString(),"ValidAddress");
    }
}