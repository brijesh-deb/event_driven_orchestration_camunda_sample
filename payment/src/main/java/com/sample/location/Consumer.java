package com.sample.location;

import example.avro.CartPayment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class Consumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);

    @Autowired
    private Producer producer;

    // Consume CartPayment from Kafka topic
    @StreamListener(Processor.INPUT)
    public void consumePaymentDetails(CartPayment payment)
    {
        LOGGER.info("Inside Payment.Consumer.consumePaymentDetails Cart Id :"+payment.getCardId());
        LOGGER.info("Inside Payment.Consumer.consumePaymentDetails Amount :"+payment.getAmount());

        // Add business logic to reserve payment amount

        // Set status based on payment reservation status; publish to Kafka
        producer.producePaymentStatus(payment.getCardId().toString(),"PaymentReserved");
    }
}