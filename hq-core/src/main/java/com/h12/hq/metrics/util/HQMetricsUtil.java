package com.h12.hq.metrics.util;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.Counter;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.prometheus.client.CollectorRegistry;

public class HQMetricsUtil {
    protected static final CollectorRegistry hqCollectorRegistry = new CollectorRegistry(true);
    private static final PrometheusMeterRegistry hqPrometheusRegistry;

    static {
        hqPrometheusRegistry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT,
                hqCollectorRegistry, Clock.SYSTEM);
        hqPrometheusRegistry.config().commonTags("application", "hq-application");
    }

    public static Counter counter(String name, String ...tags) {
        return hqPrometheusRegistry.counter(name, tags);
    }

    public static CollectorRegistry getCollectorRegistry() {
        return hqCollectorRegistry;
    }
}
