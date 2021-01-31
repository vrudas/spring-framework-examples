package io.sfe.properties.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class PropertiesJavaConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer dbProperties() {
        var configurer = new PropertySourcesPlaceholderConfigurer();

        ClassPathResource dbProperties = new ClassPathResource("db.properties");

        configurer.setLocations(dbProperties);

        return configurer;
    }
}
