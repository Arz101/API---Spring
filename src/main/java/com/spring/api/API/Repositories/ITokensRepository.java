package com.spring.api.API.Repositories;

import com.spring.api.API.models.Tokens;
import com.spring.api.API.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.Optional;

public interface ITokensRepository extends JpaRepository<Tokens, Long> {
    @Query("""
        SELECT t
        FROM Tokens t
        WHERE t.tokenHash =:tokenHash AND t.revoked =:revoked
    """)
    Optional<Tokens> findByTokenHashAndRevoked(@Param("tokenHash") String tokenHash, @Param("revoked") Boolean revoked);

    @Modifying
    @Query("""
        UPDATE Tokens t 
        SET t.revoked = true 
        WHERE t.assignedTo = :user AND t.revoked = false
    """)
    void revokeAllByUser(@Param("user") User user);

    @Query("""
        SELECT t
        FROM Tokens t
        WHERE t.assignedTo.id =:user_id AND t.revoked=false
    """)
    Tokens getCurrentTokenByUser(@Param("user_id") Long user_id);

    @Query("""
        SELECT COUNT(*) >= 1
        FROM Tokens t
        WHERE t.assignedTo.id =:user_id AND t.revoked = false
    """)
    Boolean existsAnActiveToken(@Param("user_id") Long user_id);
}
