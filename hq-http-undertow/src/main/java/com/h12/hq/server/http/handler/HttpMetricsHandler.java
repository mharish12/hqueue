package com.h12.hq.server.http.handler;

import com.h12.hq.DependencyManager;
import com.h12.hq.metrics.util.HQMetricsUtil;
import com.h12.hq.server.http.AbstractHandler;
import io.micrometer.core.instrument.Tag;
import io.prometheus.client.exporter.common.TextFormat;
import io.undertow.server.HttpServerExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.Set;

public class HttpMetricsHandler extends AbstractHandler implements io.undertow.server.HttpHandler {
    private static final Logger LOG = LoggerFactory.getLogger(HttpMetricsHandler.class);
    private final DependencyManager dependencyManager;

    public HttpMetricsHandler(DependencyManager dependencyManager) {
        this.dependencyManager = dependencyManager;
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        Iterable<Tag> tags = new ArrayList<>();
        HQMetricsUtil.gauge("mbean_count", tags, ManagementFactory.getPlatformMBeanServer().getMBeanCount());
        HQMetricsUtil.summary("mbean_heap_memory", tags).record(memoryMXBean.getHeapMemoryUsage().getCommitted());
        HQMetricsUtil.gauge("mbean_thread_count", tags, threadMXBean.getThreadCount());
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        Writer writer = new StringWriter();
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        Iterable<Tag> tags = new ArrayList<>();
        HQMetricsUtil.gauge("mbean_count", tags, ManagementFactory.getPlatformMBeanServer().getMBeanCount());
        HQMetricsUtil.summary("mbean_heap_memory", tags).record(memoryMXBean.getHeapMemoryUsage().getCommitted());
        HQMetricsUtil.gauge("mbean_thread_count", tags, threadMXBean.getThreadCount());

        TextFormat.write004(writer, dependencyManager.getAppContext().getCollectorRegistry().metricFamilySamples());
        exchange.setStatusCode(200).getResponseSender().send(writer.toString());
    }
}
