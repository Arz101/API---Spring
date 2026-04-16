package com.spring.api.API.users;

import com.spring.api.API.auth.dtos.UserDetailCredentials;
import com.spring.api.API.users.dtos.UserFound;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
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
        SELECT new com.spring.api.API.auth.dtos.UserDetailCredentials(
            u.id,
            u.username,
            u.password,
            u.status
        )
        FROM User u
        WHERE u.username =:username
    """)
    Optional<UserDetailCredentials> getCredentials(@Param("username") String username);

    @EntityGraph(attributePaths = {"following", "followers"})
    @Query("SELECT u FROM User u WHERE u.id =:id")
    User findByIdWithGraph(Long id);

    @Query("""
        SELECT new com.spring.api.API.users.dtos.UserFound(
            u.id,
            u.username,
            p.avatarUrl
        )
        FROM User u
        INNER JOIN Profiles p ON u.id = p.user.id
        WHERE u.username LIKE LOWER(CONCAT(:text, '%'))
    """)
    List<UserFound> usersFoundByText(@Param("text") String text);
}
