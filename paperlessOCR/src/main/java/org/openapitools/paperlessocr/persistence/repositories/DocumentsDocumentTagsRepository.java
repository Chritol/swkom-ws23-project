package org.openapitools.paperlessocr.persistence.repositories;

import org.openapitools.paperlessocr.persistence.entities.DocumentsDocumentTags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentsDocumentTagsRepository extends JpaRepository<DocumentsDocumentTags, Integer> {
}
