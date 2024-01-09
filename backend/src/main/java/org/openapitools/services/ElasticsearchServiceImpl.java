package org.openapitools.services;

import lombok.extern.slf4j.Slf4j;
import org.openapitools.persistence.elasticsearch.entities.ElasticDocumentDocument;
import org.openapitools.persistence.elasticsearch.repositories.ElasticDocumentDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ElasticsearchServiceImpl implements ElasticsearchService{
    private final ElasticDocumentDocumentRepository elasticsearchRepository;

    @Autowired
    public ElasticsearchServiceImpl(ElasticDocumentDocumentRepository elasticsearchRepository) {
        this.elasticsearchRepository = elasticsearchRepository;

        log.info("ElasticsearchServiceImpl created");
    }

    @Override
    public Page<ElasticDocumentDocument> fuzzySearch(String query, int page, int size) {
        log.info("Searching for documents with query: {}", query);

        Page<ElasticDocumentDocument> result = elasticsearchRepository.fuzzySearch(query, Pageable.ofSize(size).withPage(page));
        log.debug("Found {} documents", result.getTotalElements());

        return result;
    }
}
