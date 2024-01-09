package org.openapitools.paperlessocr.services;

import org.openapitools.paperlessocr.persistence.elasticsearch.entities.ElasticDocumentDocument;

import java.util.Optional;

public interface ElasticsearchService {
    void addDocument(ElasticDocumentDocument document);
    void deletedDocumentById(Integer id);

    Optional<ElasticDocumentDocument> findById(Integer id);
}
