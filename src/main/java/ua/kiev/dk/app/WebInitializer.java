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

//    @Override
//    public void onStartup(ServletContext servletContext) throws ServletException {
//        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
//        ctx.register(AppMVCConfig.class);
//
//        ctx.setServletContext(servletContext);
//
//        ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(ctx));
//        servlet.addMapping("/");
//        servlet.setLoadOnStartup(1);
//    }
//}

    public void onStartup(ServletContext container) {
        AnnotationConfigWebApplicationContext persistenceContext = new AnnotationConfigWebApplicationContext();
        persistenceContext.register(PersistenceContext.class);

        AnnotationConfigWebApplicationContext appConfig = new AnnotationConfigWebApplicationContext();
        appConfig.register(MVCContext.class);

        container.addListener(new ContextLoaderListener(persistenceContext));

        ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher", new DispatcherServlet(appConfig));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

//        FilterRegistration charEncodingfilterReg = container.addFilter("CharacterEncodingFilter", CharacterEncodingFilter.class);
//        charEncodingfilterReg.setInitParameter("encoding", "UTF-8");
//        charEncodingfilterReg.setInitParameter("forceEncoding", "true");
//        charEncodingfilterReg.addMappingForUrlPatterns(null, false, "/*");
    }
}