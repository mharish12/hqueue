package com.h12.ecommerce.controllers;

import com.h12.hqueue.di.annotation.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Controller
public class ExampleController {


    @GET
    @Path("/string")
    public String getString() {
        return "TESTING";
    }
}
