package ua.kiev.dk.app;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import ua.kiev.dk.config.MVCContext;
import ua.kiev.dk.config.PersistenceContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public class WebInitializer implements WebApplicationInitializer {

    public void onStartup(ServletContext container) {
        AnnotationConfigWebApplicationContext persistenceContext = new AnnotationConfigWebApplicationContext();
        persistenceContext.register(PersistenceContext.class);

        AnnotationConfigWebApplicationContext appConfig = new AnnotationConfigWebApplicationContext();
        appConfig.register(MVCContext.class);

        container.addListener(new ContextLoaderListener(persistenceContext));

        ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher", new DispatcherServlet(appConfig));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }
}