package com.sample.shoppingcart;

import com.sample.shoppingcart.models.RestMessageDto;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import example.avro.ShoppingCartStatus;

@Service
public class ShoppingCartConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingCartConsumer.class);
    private static final String REST_ENDPOINT = "http://localhost:9001/engine-rest/message";

    // Consume Status from Kafka topic
    @KafkaListener(topics = "shoppingcart.status", groupId = "group_id")
    public void consume(ConsumerRecord<String, ShoppingCartStatus> record)
    {
        String message ="";
        String cart_id = record.value().getCartId().toString();
        String status = record.value().getStatus().toString();

        LOGGER.info(" Inside ShoppingCartApplication:Consumer:Status = "+status);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        if(status.equals("ValidAddress"))
            message ="AddressValidated";
        else if (status.equals("PaymentReserved"))
            message ="PaymentReserved";
        HttpEntity<RestMessageDto> entity = new HttpEntity<RestMessageDto>(
                new RestMessageDto(message,cart_id),
                headers);

        ResponseEntity<Object> result = new RestTemplate().postForEntity(REST_ENDPOINT, entity, null);
    }
}