package com.h12.ecommerce.model;

import com.h12.hq.di.annotation.Value;

import java.util.List;

public class Product {
    @Value(value = "hq.hello", defaultValue = "Hello default")
    private String name;
    @Value(value = "hq.list.names")
    private List<String> listNames;
    @Value(value = "hq.arrays.names")
    private String[] arrayNames;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getListNames() {
        return listNames;
    }

    public void setListNames(List<String> listNames) {
        this.listNames = listNames;
    }

    public String[] getArrayNames() {
        return arrayNames;
    }

    public void setArrayNames(String[] arrayNames) {
        this.arrayNames = arrayNames;
    }
}
