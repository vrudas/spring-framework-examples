package io.sfe.dispatcher.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public class AppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) {
        var webApplicationContext = new AnnotationConfigWebApplicationContext();
        webApplicationContext.register(WebAppConfig.class);

        // Manage the lifecycle of the root application context
        servletContext.addListener(new ContextLoaderListener(webApplicationContext));

        // Register and map the dispatcher servlet
        ServletRegistration.Dynamic dispatcher = servletContext
            .addServlet(
                "dispatcher",
                new DispatcherServlet(webApplicationContext)
            );

        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }

}
