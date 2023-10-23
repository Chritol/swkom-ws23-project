package org.openapitools.persistence.repositories;

import org.openapitools.persistence.entities.AuthGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthGroupRepository extends JpaRepository<AuthGroup, Integer> {
}
