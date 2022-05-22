package com.sample.shoppingcart.delegates;

import com.sample.shoppingcart.models.Cart;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.apache.kafka.clients.producer.ProducerRecord;
import example.avro.ShippingAddress;

import org.springframework.beans.factory.annotation.Autowired;

@Component
public class ValidateAddressDelegate implements JavaDelegate
{
    private final Logger LOGGER = LoggerFactory.getLogger(ValidateAddressDelegate.class.getName());
    private static final String TOPIC = "shoppingcart.address-validate.address";

    @Autowired
    private KafkaTemplate<String, ShippingAddress> kafkaTemplate;

    public void execute(DelegateExecution execution)
    {
        Cart cart = (Cart)execution.getVariable("cart");
        String cart_id = cart.getCart_id();
        String street = cart.getShippingAddress().getStreet();
        String state = cart.getShippingAddress().getState();
        String post_code = cart.getShippingAddress().getPost_code();
        ShippingAddress address = new ShippingAddress(cart_id,street,state,post_code);

        // Publish ShippingAddress to Kafka topic
        ProducerRecord<String,ShippingAddress> producerRecord = new ProducerRecord<>(TOPIC,0,null,cart_id,address);
        this.kafkaTemplate.send(TOPIC, address);
    }
}