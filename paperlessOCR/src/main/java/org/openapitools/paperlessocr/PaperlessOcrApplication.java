package org.openapitools.paperlessocr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("org.openapitools.paperlessocr.persistence.repositories")
public class PaperlessOcrApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaperlessOcrApplication.class, args);
    }

}
