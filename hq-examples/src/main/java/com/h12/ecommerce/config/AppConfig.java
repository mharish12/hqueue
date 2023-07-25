package com.h12.ecommerce.config;

import com.h12.ecommerce.model.Product;
import com.h12.hq.di.annotation.Bean;
import com.h12.hq.di.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public Product getModel() {
        return new Product();
    }
}
