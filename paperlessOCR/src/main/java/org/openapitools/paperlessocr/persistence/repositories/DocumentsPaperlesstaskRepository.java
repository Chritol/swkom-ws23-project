package org.openapitools.paperlessocr.persistence.repositories;

import org.openapitools.paperlessocr.persistence.entities.DocumentsPaperlesstask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentsPaperlesstaskRepository extends JpaRepository<DocumentsPaperlesstask, Integer> {
}
