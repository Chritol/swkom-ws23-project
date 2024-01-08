package org.openapitools.paperlessocr.persistence.repositories;

import org.openapitools.paperlessocr.persistence.entities.PaperlessMailMailruleAssignTags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaperlessMailMailruleAssignTagsRepository extends JpaRepository<PaperlessMailMailruleAssignTags, Integer> {
}
