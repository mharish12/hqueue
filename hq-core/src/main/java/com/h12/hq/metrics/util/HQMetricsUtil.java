package com.h12.hq.metrics.util;

import io.micrometer.core.instrument.*;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Gauge;

public class HQMetricsUtil {
    private static final CollectorRegistry hqCollectorRegistry = new CollectorRegistry(true);
    public static final PrometheusMeterRegistry hqPrometheusRegistry;

    static {
        hqPrometheusRegistry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT,
                hqCollectorRegistry, Clock.SYSTEM);
        hqPrometheusRegistry.config().commonTags("application", "hq-application");
        Metrics.addRegistry(hqPrometheusRegistry);
    }

    public static Counter counter(String name, String... tags) {
        return Metrics.counter(name, tags);
    }

    public static <T extends Number> T gauge(String name, Iterable<Tag> tags, T number) {
        return Metrics.gauge(name, tags, number);
    }

    public static DistributionSummary summary(String name, String... tags) {
        return Metrics.summary(name, tags);
    }

    public static DistributionSummary summary(String name, Iterable<Tag> tags) {
        return Metrics.summary(name, tags);
    }

    public static CollectorRegistry getCollectorRegistry() {
        return hqCollectorRegistry;
    }
}
