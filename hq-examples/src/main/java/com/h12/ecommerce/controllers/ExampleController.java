package com.h12.ecommerce.controllers;

import com.h12.ecommerce.service.ExampleService;
import com.h12.hq.di.annotation.AutoWire;
import com.h12.hq.di.annotation.Controller;
import com.h12.hq.metrics.util.HQMetricsUtil;
import io.micrometer.core.instrument.Counter;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Controller
@Path("/example")
public class ExampleController {

    @AutoWire
    private ExampleService exampleService;
    private transient final Counter getCallCounter;

    public ExampleController() {
        getCallCounter = HQMetricsUtil.counter("get_string_end_point");
    }

    @GET
    @Path("/string")
    public String getString() {
        getCallCounter.increment();
        return exampleService.getString();
    }
}
