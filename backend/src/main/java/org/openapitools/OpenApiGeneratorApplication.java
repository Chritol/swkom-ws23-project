package org.openapitools;

import com.fasterxml.jackson.databind.Module;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FullyQualifiedAnnotationBeanNameGenerator;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

<<<<<<< Updated upstream
@SpringBootApplication(scanBasePackages = "org.openapitools")
=======
@SpringBootApplication(
    nameGenerator = FullyQualifiedAnnotationBeanNameGenerator.class
)
@ComponentScan(
    basePackages = {"org.openapitools", "org.openapitools.api" , "org.openapitools.configuration", "org.openapitools.service", "org.openapitools.mapper", "org.openapitools.persistence.entities", "org.openapitools.persistence.repositories"},
    nameGenerator = FullyQualifiedAnnotationBeanNameGenerator.class
)
>>>>>>> Stashed changes
@EnableJpaRepositories("org.openapitools.persistence.repositories")
public class OpenApiGeneratorApplication {

    public static void main(String[] args) {


        SpringApplication.run(OpenApiGeneratorApplication.class, args);
    }

    @Bean(name = "org.openapitools.OpenApiGeneratorApplication.jsonNullableModule")
    public Module jsonNullableModule() {
        return new JsonNullableModule();
    }

}