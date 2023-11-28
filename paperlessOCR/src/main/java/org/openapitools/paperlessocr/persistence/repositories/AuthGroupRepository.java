package org.openapitools.paperlessocr.persistence.repositories;

import org.openapitools.paperlessocr.persistence.entities.AuthGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthGroupRepository extends JpaRepository<AuthGroup, Integer> {
}
