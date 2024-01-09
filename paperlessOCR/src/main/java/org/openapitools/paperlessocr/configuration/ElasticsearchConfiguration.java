package org.openapitools.paperlessocr.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(
        basePackages = "org.openapitools.paperlessocr.persistence.elasticsearch.repositories"
)
public class ElasticsearchConfiguration extends org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration {
    @Value("${elasticsearch.host}")
    private String elasticHost;

    @Value("${spring.elasticsearch.username}")
    private String username;

    @Value("${spring.elasticsearch.password}")
    private String password;

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(elasticHost)
                .withBasicAuth(username, password)
                .build();
    }
}
