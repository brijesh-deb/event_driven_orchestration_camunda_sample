package com.sample.shoppingcart.delegates;

import com.sample.shoppingcart.models.Cart;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import example.avro.CartPayment;

@Component
public class ReservePaymentDelegate implements JavaDelegate
{
    private final Logger LOGGER = LoggerFactory.getLogger(ReservePaymentDelegate.class.getName());
    private static final String TOPIC = "shoppingcart.reserve-payment.payment";

    @Autowired
    private KafkaTemplate<String, CartPayment> kafkaTemplate;

    public void execute(DelegateExecution execution)
    {
        Cart cart = (Cart)execution.getVariable("cart");
        String cart_id = cart.getCart_id();
        String amount = cart.getAmount();
        CartPayment payment = new CartPayment(cart_id,amount);

        // Publish CartPayment to Kafka topic
        ProducerRecord<String,CartPayment> producerRecord = new ProducerRecord<>(TOPIC,0,null,cart_id,payment);
        this.kafkaTemplate.send(TOPIC, payment);
    }
}