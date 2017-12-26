package com.flipkart.container.init;

import com.flipkart.manager.JMXReporterManager;
import io.dropwizard.lifecycle.Managed;

import javax.inject.Inject;

public class ContainerManager implements Managed {

    @Inject
    private JMXReporterManager jmxReporterManager;

    @Override
    public void start() throws Exception {
        jmxReporterManager.start();
    }

    @Override
    public void stop() throws Exception {
        jmxReporterManager.stop();
    }
}
