package org.openapitools.paperlessocr.persistence.elasticsearch.entities;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "document")
@Builder
public class ElasticDocumentDocument {

    @Id
    private Integer id;

    private String title;

    private String content;

    private String filename;
}
