package org.openapitools.paperlessocr.persistence.elasticsearch.repositories;

import org.openapitools.paperlessocr.persistence.elasticsearch.entities.ElasticDocumentDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElasticDocumentDocumentRepository extends ElasticsearchRepository<ElasticDocumentDocument, Integer> {
}
