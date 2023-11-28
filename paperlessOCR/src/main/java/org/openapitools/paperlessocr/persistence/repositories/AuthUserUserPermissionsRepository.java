package org.openapitools.paperlessocr.persistence.repositories;

import org.openapitools.paperlessocr.persistence.entities.AuthUserUserPermissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthUserUserPermissionsRepository extends JpaRepository<AuthUserUserPermissions, Integer> {
}
