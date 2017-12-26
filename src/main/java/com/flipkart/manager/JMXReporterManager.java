package com.flipkart.manager;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import com.google.inject.Inject;
import com.netflix.hystrix.contrib.codahalemetricspublisher.HystrixCodaHaleMetricsPublisher;
import com.netflix.hystrix.strategy.HystrixPlugins;

public class JMXReporterManager {

    private MetricRegistry metricRegistry;
    JmxReporter reporter;

    @Inject
    public JMXReporterManager(MetricRegistry metricRegistry) {
        this.metricRegistry = metricRegistry;
    }


    public void start() throws Exception {
        reporter = JmxReporter.forRegistry(metricRegistry).build();
        reporter.start();

        HystrixPlugins.reset();
        HystrixCodaHaleMetricsPublisher publisher = new HystrixCodaHaleMetricsPublisher(metricRegistry);
        HystrixPlugins.getInstance().registerMetricsPublisher(publisher);

    }


    public void stop() throws Exception {
        reporter.close();
    }

}
