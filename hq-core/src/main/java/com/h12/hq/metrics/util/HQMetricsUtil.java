package com.h12.hq.metrics.util;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.MetricSet;
import com.codahale.metrics.jvm.*;
import com.h12.hq.util.StringConstants;
import io.micrometer.core.instrument.*;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.dropwizard.DropwizardExports;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HQMetricsUtil {
    private static final CollectorRegistry hqCollectorRegistry = CollectorRegistry.defaultRegistry;
    private static final PrometheusMeterRegistry hqPrometheusRegistry;
    private static final MetricRegistry metricRegistry;

    static {
        hqPrometheusRegistry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT, hqCollectorRegistry, Clock.SYSTEM);
        hqPrometheusRegistry.config().commonTags("application", "hq-application");
        Metrics.addRegistry(hqPrometheusRegistry);
        metricRegistry = new MetricRegistry();
        hqCollectorRegistry.register(new DropwizardExports(metricRegistry));

        final LoggerContext factory = (LoggerContext) LoggerFactory.getILoggerFactory();
        final ch.qos.logback.classic.Logger root = factory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        final ConsoleAppender<ILoggingEvent> metrics = new ConsoleAppender<ILoggingEvent>();
        metrics.setContext(root.getLoggerContext());
        metrics.start();
        root.addAppender(metrics);
        registerAll("gc", new GarbageCollectorMetricSet(), metricRegistry);
        registerAll("buffers", new BufferPoolMetricSet(ManagementFactory.getPlatformMBeanServer()), metricRegistry);
        registerAll("memory", new MemoryUsageGaugeSet(), metricRegistry);
        registerAll("threads", new ThreadStatesGaugeSet(), metricRegistry);
        registerAll("cached_thread_states", new CachedThreadStatesGaugeSet(60, TimeUnit.SECONDS), metricRegistry);
        registerAll("class_loading", new ClassLoadingGaugeSet(), metricRegistry);
    }

    private static void registerAll(String prefix, MetricSet metricSet, MetricRegistry registry) {
        for (Map.Entry<String, Metric> entry : metricSet.getMetrics().entrySet()) {
            if (entry.getValue() instanceof MetricSet) {
                registerAll(prefix + StringConstants.UNDER_SCORE + entry.getKey(), (MetricSet) entry.getValue(), registry);
            } else {
                registry.register(prefix + StringConstants.UNDER_SCORE + entry.getKey(), entry.getValue());
            }
        }
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

    public static PrometheusMeterRegistry getHqPrometheusRegistry() {
        return hqPrometheusRegistry;
    }
}
