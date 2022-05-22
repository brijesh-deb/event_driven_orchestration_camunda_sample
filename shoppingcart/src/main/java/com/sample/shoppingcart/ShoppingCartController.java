package com.sample.shoppingcart;

import com.sample.shoppingcart.models.Cart;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/shoppingcart")
public class ShoppingCartController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingCartController.class);

    @PostMapping
    public void startCartWorkflow(@RequestBody Cart cart)
    {
        LOGGER.info("Inside ShoppingCartController.startCartWorkflow");
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService=processEngine.getRuntimeService();

        Map<String, Object> variables=new HashMap<>();
        variables.put("cart",cart);

        ProcessInstance instance = runtimeService.createProcessInstanceByKey("shoppingcart_workflow")
                .businessKey(cart.getCart_id())
                .setVariables(variables)
                .execute();
    }
}