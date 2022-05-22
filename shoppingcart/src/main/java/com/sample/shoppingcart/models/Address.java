package com.sample.shoppingcart.models;

public class Address
{
    private String street;
    private String state;
    private String post_code;

    public String getStreet() {
        return street;
    }

    public String getState() {
        return state;
    }

    public String getPost_code() {
        return post_code;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setPost_code(String post_code) {
        this.post_code = post_code;
    }
}