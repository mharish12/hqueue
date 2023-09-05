package com.h12.hq.metrics.pushgateway;

import com.h12.hq.metrics.util.HQMetricsUtil;
import io.prometheus.client.exporter.PushGateway;

import java.io.IOException;

public class HQPushGateway {
    private static PushGateway pushGateway;
//    static  {
//        pushGateway = new PushGateway("");
//    }

    public HQPushGateway() {
        pushGateway = new PushGateway("");
    }

    public boolean push() {
        boolean isPushed = false;
        try {
            pushGateway.push(HQMetricsUtil.getCollectorRegistry(), "hq_job");
            isPushed = true;
        } catch (IOException e) {

        }
        return isPushed;
    }
}
