package org.openapitools.paperlessocr.persistence.elasticsearch.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "document")
@Builder
@Getter
@ToString
public class ElasticDocumentDocument {

    @Id
    private Integer id;

    private String title;

    private String content;

    private String filename;
}
