package com.h12.ecommerce.dao;

import com.h12.hq.di.annotation.Component;

import java.util.HashMap;
import java.util.Map;

@Component()
public class ExampleDao {
    Map<String, String> daoMap;

    public ExampleDao() {
        this.daoMap = new HashMap<>();
    }

    public String getString(String key) {
        return daoMap.get(key);
    }
}
