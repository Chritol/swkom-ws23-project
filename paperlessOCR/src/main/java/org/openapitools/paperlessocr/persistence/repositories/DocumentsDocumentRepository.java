package org.openapitools.paperlessocr.persistence.repositories;

import org.openapitools.paperlessocr.persistence.entities.DocumentsDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentsDocumentRepository extends JpaRepository<DocumentsDocument, Integer> {
}
