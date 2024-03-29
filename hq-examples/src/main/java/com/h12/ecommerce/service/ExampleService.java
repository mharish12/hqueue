package com.h12.ecommerce.service;

import com.h12.ecommerce.dao.ExampleDao;
import com.h12.ecommerce.model.Product;
import com.h12.hq.di.annotation.AutoWire;
import com.h12.hq.di.annotation.Service;

@Service
public class ExampleService extends HelloService {
    @AutoWire
    private ExampleDao exampleDao;

    public String getString() {
        return exampleDao.getString("");
    }

}
