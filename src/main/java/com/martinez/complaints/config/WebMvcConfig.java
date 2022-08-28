package com.martinez.complaints.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.ResourceBundle;

@Slf4j
@PropertySources(value = {
        @PropertySource(value = "classpath:messages.properties"),
        @PropertySource(value = "classpath:messages_en.properties"),
        @PropertySource(value = "classpath:messages_es.properties")
})
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Value("#{environment['app.language']}")
    private String language;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("POST", "GET", "PUT");
    }

    @Bean
    ResourceBundle resourceBundle(){
        Locale.setDefault(new Locale(language));
        return ResourceBundle.getBundle("messages", new Locale(language));
    }

    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean factory = new LocalValidatorFactoryBean();
        factory.setValidationMessageSource(messageSource());
        return factory;
    }

    private MessageSource messageSource() {
        log.info("Language: {}", language);
        var messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultLocale(new Locale(language));
        messageSource.setFallbackToSystemLocale(false);
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.displayName());
        return messageSource;
    }

}
