package org.openapitools.paperlessocr.persistence.repositories;

import org.openapitools.paperlessocr.persistence.entities.DocumentsSavedviewfilterrule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentsSavedviewfilterruleRepository extends JpaRepository<DocumentsSavedviewfilterrule, Integer> {
}
