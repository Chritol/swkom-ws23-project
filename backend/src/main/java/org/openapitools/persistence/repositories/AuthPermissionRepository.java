package org.openapitools.persistence.repositories;

import org.openapitools.persistence.entities.AuthPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthPermissionRepository extends JpaRepository<AuthPermission, Integer> {
}
