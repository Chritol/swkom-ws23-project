package org.openapitools.services;

import org.openapitools.persistence.elasticsearch.entities.ElasticDocumentDocument;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ElasticsearchService {
    Page<ElasticDocumentDocument> fuzzySearch(String query, int page, int size);
}
