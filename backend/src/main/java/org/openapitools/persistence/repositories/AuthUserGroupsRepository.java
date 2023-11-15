package org.openapitools.persistence.repositories;

import org.openapitools.persistence.entities.AuthUserGroups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthUserGroupsRepository extends JpaRepository<AuthUserGroups, Integer> {
}
