package org.openapitools.persistence.repositories;

import org.openapitools.persistence.entities.DocumentsLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentsLogRepository extends JpaRepository<DocumentsLog, Integer> {
}
