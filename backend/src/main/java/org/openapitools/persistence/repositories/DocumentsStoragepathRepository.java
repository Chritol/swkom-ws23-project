package org.openapitools.persistence.repositories;

import org.openapitools.persistence.entities.DocumentsStoragepath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentsStoragepathRepository extends JpaRepository<DocumentsStoragepath, Integer> {
}
