package com.flipkart;

import com.flipkart.container.init.ContainerManager;
import com.flipkart.core.module.DroolsModule;
import com.flipkart.filter.RequestFilter;
import com.flipkart.resource.DummyResource;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

public class DroolsApplications extends Application<DroolsConfiguration>{


    public static void main(String[] args) throws Exception {
        new DroolsApplications().run(args);
    }
    protected DroolsApplications() {
        super();
    }

    @Override
    public String getName() {
        return "Drools Demo Application";
    }

    @Override
    public void initialize(Bootstrap<DroolsConfiguration> bootstrap) {
        super.initialize(bootstrap);
    }

    @Override
    public void run(DroolsConfiguration configuration, Environment environment) throws Exception {
        Injector injector =
                Guice.createInjector(new DroolsModule());
        registerJerseyResources(environment,injector);
        environment.lifecycle().manage(injector.getInstance(ContainerManager.class));
        environment.getApplicationContext().addServlet(
                "com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet", "/hystrix.stream");
        environment.servlets().addFilter("dummy", new RequestFilter())
                .addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST),true,"/drools/v1/client/*");
    }

    private void registerJerseyResources(Environment environment, Injector injector) {
        environment.jersey().register(injector.getInstance(DummyResource.class));
    }
}
