package com.spring.api.API.emails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface IEmailTokensRepository extends JpaRepository<EmailTokens, Long> {
    @Query("SELECT t FROM EmailTokens t WHERE t.tokenHash = :token")
    EmailTokens findToken(@Param("token") String token);
}
