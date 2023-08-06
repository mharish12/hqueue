package com.h12.ecommerce.dao;

import com.h12.hq.di.annotation.Component;
import com.h12.hq.hooks.ShutDownHook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Component()
public class ExampleDao extends ShutDownHook {
    private final Logger logger = LoggerFactory.getLogger(ExampleDao.class);
    private final Map<String, String> daoMap;

    public ExampleDao() {
        this.daoMap = new HashMap<>();
    }

    public String getString(String key) {
        return "TEST STRING";
    }

    public void put(String key, String value) {
        daoMap.put(key, value);
    }

    @Override
    public void shutdown() {
        logger.info(this.getClass().getName() + " Called shutdown hook.");
    }
}
