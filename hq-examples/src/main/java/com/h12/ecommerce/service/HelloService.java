package com.h12.ecommerce.service;

import com.h12.hq.di.annotation.Value;

public class HelloService {
    @Value("hq.hello")
    private String value;

    public String getString() {
        return value;
    }
}
