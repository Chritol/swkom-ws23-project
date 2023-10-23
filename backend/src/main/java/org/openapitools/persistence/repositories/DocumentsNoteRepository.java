package org.openapitools.persistence.repositories;

import org.openapitools.persistence.entities.DocumentsNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentsNoteRepository extends JpaRepository<DocumentsNote, Integer> {
}
