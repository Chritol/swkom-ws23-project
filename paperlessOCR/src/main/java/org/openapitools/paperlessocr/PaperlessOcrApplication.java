package org.openapitools.paperlessocr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FullyQualifiedAnnotationBeanNameGenerator;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
        nameGenerator = FullyQualifiedAnnotationBeanNameGenerator.class
)
@ComponentScan(
        basePackages = {"org.openapitools.paperlessocr", "org.openapitools.paperlessocr.configuration", "org.openapitools.paperlessocr.services", "org.openapitools.paperlessocr.services", "org.openapitools.paperlessocr.persistence.entities", "org.openapitools.paperlessocr.persistence.repositories"},
        nameGenerator = FullyQualifiedAnnotationBeanNameGenerator.class
)
@EntityScan("org.openapitools.paperlessocr.persistence.entities")
@EnableJpaRepositories("org.openapitools.paperlessocr.persistence.repositories")
public class PaperlessOcrApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaperlessOcrApplication.class, args);
    }

}
