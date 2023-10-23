package org.openapitools.persistence.repositories;

import org.openapitools.persistence.entities.DocumentsSavedviewfilterrule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentsSavedviewfilterruleRepository extends JpaRepository<DocumentsSavedviewfilterrule, Integer> {
}
