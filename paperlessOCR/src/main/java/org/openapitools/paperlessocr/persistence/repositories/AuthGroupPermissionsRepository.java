package org.openapitools.paperlessocr.persistence.repositories;

import org.openapitools.paperlessocr.persistence.entities.AuthGroupPermissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthGroupPermissionsRepository extends JpaRepository<AuthGroupPermissions, Integer> {
}
