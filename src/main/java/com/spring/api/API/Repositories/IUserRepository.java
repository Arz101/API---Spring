package com.spring.api.API.Repositories;

import com.spring.api.API.models.DTOs.Auth.UserDetailCredentials;
import com.spring.api.API.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {
    @Query(" SELECT u FROM User u WHERE u.username =:username")
    Optional<User> findByUsername(@Param("username") String username);

    @Query("SELECT COUNT(*) > 1 FROM User u WHERE u.username =:username")
    Boolean existsByUsername(@Param("username") String username);

    @Query("SELECT COUNT(*) > 1 FROM User u WHERE u.email =:email")
    Boolean existsByEmail(@Param("email") String email);

    @Query("SELECT u.id FROM User u WHERE u.username =:username")
    Optional<Long> getIdByUsername(@Param("username") String username);

    @Query("""
        SELECT new com.spring.api.API.models.DTOs.Auth.UserDetailCredentials(
            u.username,
            u.password,
            u.status
        )
        FROM User u
        WHERE u.username =:username
    """)
    Optional<UserDetailCredentials> getCredentials(@Param("username") String username);
}
