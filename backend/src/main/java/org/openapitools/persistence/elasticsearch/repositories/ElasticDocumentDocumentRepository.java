package org.openapitools.persistence.elasticsearch.repositories;

import org.openapitools.persistence.elasticsearch.entities.ElasticDocumentDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElasticDocumentDocumentRepository extends ElasticsearchRepository<ElasticDocumentDocument, Integer> {
    @Query("{\"bool\": {\"should\": [{\"match\": {\"title\": {\"query\": \"?0\",\"fuzziness\": \"AUTO\"}}},{\"match\": {\"content\": {\"query\": \"?0\",\"fuzziness\": \"AUTO\"}}},{\"match\": {\"filename\": {\"query\": \"?0\",\"fuzziness\": \"AUTO\"}}}]}}")
    Page<ElasticDocumentDocument> fuzzySearch(String query, Pageable pageable);
}
