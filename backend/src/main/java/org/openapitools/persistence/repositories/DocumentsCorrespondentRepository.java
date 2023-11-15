package org.openapitools.persistence.repositories;

import org.openapitools.persistence.entities.DocumentsCorrespondent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentsCorrespondentRepository extends JpaRepository<DocumentsCorrespondent, Integer> {
}
