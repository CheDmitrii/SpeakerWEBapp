package ru.dmitrii.speakerWEBapp.config;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import java.util.EnumSet;

public class MySpringMvcDispatcherSerlvetIntitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {SpringConfig.class, SecurityConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }

    @Override
    public void onStartup(ServletContext aServletContext) throws ServletException {
        super.onStartup(aServletContext);
        registerHiddenFieldFilter(aServletContext);
    }

    private void registerHiddenFieldFilter(ServletContext aContext) {
        aContext.addFilter("hiddenHttpMethodFilter",
                new HiddenHttpMethodFilter()).addMappingForUrlPatterns(null ,true, "/*");
    }
    @Bean
    public ContextLoaderListener contextLoaderListener(WebApplicationContext context) { // ?? without exeprion LoadListner
        return new ContextLoaderListener(context);
    }
}
