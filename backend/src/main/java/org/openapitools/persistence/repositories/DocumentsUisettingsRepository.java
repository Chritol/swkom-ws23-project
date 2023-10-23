package org.openapitools.persistence.repositories;

import org.openapitools.persistence.entities.DocumentsUisettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentsUisettingsRepository extends JpaRepository<DocumentsUisettings, Integer> {
}
