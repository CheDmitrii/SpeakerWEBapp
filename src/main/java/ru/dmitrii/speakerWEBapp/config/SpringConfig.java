package ru.dmitrii.speakerWEBapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;
import org.thymeleaf.messageresolver.StandardMessageResolver;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

import javax.sql.DataSource;
import java.util.Locale;




@Configuration
@ComponentScan("ru.dmitrii.speakerWEBapp")
@EnableWebMvc
@PropertySource("classpath:/properties/DB/database.properties")
public class SpringConfig implements WebMvcConfigurer {
    private final ApplicationContext applicationContext;
    private final Environment environment;

    @Autowired
    public SpringConfig(ApplicationContext applicationContext, Environment environment) {
        this.applicationContext = applicationContext;
        this.environment = environment;
    }


    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");// russian letters works without it
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        templateEngine.addDialect(springSecurityDialect()); // for security dialect
        templateEngine.addMessageResolver(new StandardMessageResolver()); // ???? for message source
        return templateEngine;
    }

    @Bean
    public ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setCharacterEncoding("UTF-8"); // russian letters works without it
        return viewResolver;
    }

    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        resolver.setCharacterEncoding("UTF-8"); // without it russian letters doesn't work
        registry.viewResolver(resolver);
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource driver = new DriverManagerDataSource();

        driver.setDriverClassName(environment.getProperty("driver"));
        driver.setUrl(environment.getProperty("url"));
        driver.setUsername(environment.getProperty("username"));
        driver.setPassword(environment.getProperty("password"));

        return driver;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }




    @Bean(name = "messageSource")
    public ResourceBundleMessageSource getMessageSource() {  // for multi languages
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("properties/languages/messages");
//        messageSource.setUseCodeAsDefaultMessage(true);
//        messageSource.setFallbackToSystemLocale(false); // ?
//        messageSource.setCacheSeconds(0); // ?
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocaleResolver localeResolver() { // for multi languages (set default language)
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.US);
        return localeResolver;
    }

    // for multi languages
    // (set param that change language in get request param)
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) { // for multi languages
//        WebMvcConfigurer.super.addInterceptors(registry); // default method
        registry.addInterceptor(localeChangeInterceptor()); // custom
    }

    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(getMessageSource());
        return bean;
    }

    @Bean
    public SpringSecurityDialect springSecurityDialect() {
        return new SpringSecurityDialect();
    }

}