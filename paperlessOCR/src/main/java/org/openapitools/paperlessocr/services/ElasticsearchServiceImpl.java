package org.openapitools.paperlessocr.services;

import lombok.extern.slf4j.Slf4j;
import org.openapitools.paperlessocr.persistence.elasticsearch.entities.ElasticDocumentDocument;
import org.openapitools.paperlessocr.persistence.elasticsearch.repositories.ElasticDocumentDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class ElasticsearchServiceImpl implements ElasticsearchService{
    private final ElasticDocumentDocumentRepository elasticDocumentDocumentRepository;

    @Autowired
    public ElasticsearchServiceImpl(ElasticDocumentDocumentRepository elasticDocumentDocumentRepository) {
        this.elasticDocumentDocumentRepository = elasticDocumentDocumentRepository;
    }

    @Override
    public void addDocument(ElasticDocumentDocument document) {
        elasticDocumentDocumentRepository.save(document);
        log.info("Document added to Elasticsearch: " + document);
    }

    @Override
    public void deletedDocumentById(Integer id) {
        elasticDocumentDocumentRepository.deleteById(id);
    }

    @Override
    public Optional<ElasticDocumentDocument> findById(Integer id) {
        return elasticDocumentDocumentRepository.findById(id);
    }
}
