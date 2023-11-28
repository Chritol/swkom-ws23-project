package org.openapitools.paperlessocr.persistence.repositories;

import org.openapitools.paperlessocr.persistence.entities.DocumentsDocumenttype;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentsDocumenttypeRepository extends JpaRepository<DocumentsDocumenttype, Integer> {
}
