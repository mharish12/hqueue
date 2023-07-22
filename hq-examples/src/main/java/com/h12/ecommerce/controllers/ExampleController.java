package com.h12.ecommerce.controllers;

import com.h12.ecommerce.service.ExampleService;
import com.h12.hq.di.annotation.AutoWire;
import com.h12.hq.di.annotation.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Controller
public class ExampleController {

    @AutoWire
    private ExampleService exampleService;

    @GET
    @Path("/string")
    public String getString() {
        return exampleService.getString();
    }
}
