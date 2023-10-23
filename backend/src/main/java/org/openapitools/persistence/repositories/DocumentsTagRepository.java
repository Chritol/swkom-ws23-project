package org.openapitools.persistence.repositories;

import org.openapitools.persistence.entities.DocumentsTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentsTagRepository extends JpaRepository<DocumentsTag, Integer> {
}
