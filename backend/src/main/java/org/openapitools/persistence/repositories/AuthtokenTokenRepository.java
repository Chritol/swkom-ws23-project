package org.openapitools.persistence.repositories;

import org.openapitools.persistence.entities.AuthtokenToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthtokenTokenRepository extends JpaRepository<AuthtokenToken, Long> {
}
