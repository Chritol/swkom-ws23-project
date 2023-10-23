package org.openapitools.persistence.repositories;

import org.openapitools.persistence.entities.DocumentsSavedview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentsSavedviewRepository extends JpaRepository<DocumentsSavedview, Integer> {
}
