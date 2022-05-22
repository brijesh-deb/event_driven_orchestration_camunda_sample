package com.sample.shoppingcart.models;

import com.sample.shoppingcart.models.Address;

public class Cart
{
    private String cart_id;
    private Address shippingAddress;
    private String amount;

    public String getCart_id() {
        return cart_id;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public String getAmount() {
        return amount;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}