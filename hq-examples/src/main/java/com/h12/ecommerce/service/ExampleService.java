package com.h12.ecommerce.service;

import com.h12.ecommerce.dao.ExampleDao;
import com.h12.hq.di.annotation.AutoWire;
import com.h12.hq.di.annotation.Service;

@Service
public class ExampleService {
    @AutoWire
    private ExampleDao exampleDao;//TODO: Not injecting the bean here.

    public String getString() {
        return exampleDao.getString("");
    }

}
