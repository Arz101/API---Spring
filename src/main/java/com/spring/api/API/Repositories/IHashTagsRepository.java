package com.spring.api.API.Repositories;

import com.spring.api.API.models.DTOs.Posts.HashtagsDTO;
import com.spring.api.API.models.DTOs.Posts.TestPostDTO;
import com.spring.api.API.models.Hashtags;
import com.spring.api.API.models.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public interface IHashTagsRepository extends JpaRepository<Hashtags, Long> {
    @Query("SELECT h FROM Hashtags h WHERE h.name =:name ")
    Optional<Hashtags> existsHashtag(@Param("name") String name);

    @Query("""
       SELECT h.name
       FROM Hashtags h
       JOIN h.posts p
       WHERE p.id = :post_id
    """)
    Set<String> getHashtagsByPostId(@Param("post_id") Long post_id);

    @Query("""
        SELECT new com.spring.api.API.models.DTOs.Posts.HashtagsDTO(h.name, ph.id)
        FROM Hashtags h
        JOIN h.posts ph
        WHERE ph.user.id =:user_id
    """)
    List<HashtagsDTO> getAllHashtagsByUserId(@Param("user_id") Long user_id);

    @Query("""
        SELECT new com.spring.api.API.models.DTOs.Posts.HashtagsDTO(h.name, ph.id)
        FROM Hashtags h
        JOIN h.posts ph
        WHERE ph.user.id IN :users_id
    """)
    List<HashtagsDTO> getAllHashtagsByFollowingsId(@Param("users_id") List<Long> users_id);

}
