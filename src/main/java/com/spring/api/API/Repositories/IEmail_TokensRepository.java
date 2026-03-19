package com.spring.api.API.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spring.api.API.models.Email_Tokens;


@Repository
public interface IEmail_TokensRepository extends JpaRepository<Email_Tokens, Long> {
    @Query("SELECT t FROM Email_Tokens t WHERE t.token_hash = :token")
    Email_Tokens findToken(@Param("token") String token);
}
