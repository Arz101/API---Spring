package com.spring.api.API.Repositories;

import com.spring.api.API.models.Posts;
import com.spring.api.API.models.DTOs.Posts.PostResponse;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IPostsRepository extends JpaRepository<Posts, Long> {
    Optional<Posts> findById(long id);

    @Query("""
        SELECT p 
        FROM Posts p 
        WHERE p.user.username = :username
    """)
    List<Posts> findByUsername(String username);

    @Query("""
        SELECT p 
        FROM Posts p 
        WHERE p.user.username IN :followingUsernames
        ORDER BY p.datecreated DESC
    """)
    List<Posts> findByFollowingUsernames(List<String> followingUsernames);


    @Query("""
        SELECT new com.spring.api.API.models.DTOs.Posts.PostResponse(
            p.id,
            p.description,
            p.picture,
            p.user.username,
            COUNT(l.id),
            COUNT(c.id),
            p.datecreated
        )
        FROM Posts p
        LEFT JOIN Likes l ON l.post = p
        LEFT JOIN Comments c ON c.post = p
        WHERE p.user.id IN :followingUserIds
        GROUP BY 
            p.id,
            p.description,
            p.picture,
            p.user.username,
            p.datecreated
        ORDER BY p.datecreated DESC
    """)
    List<PostResponse> findFeed(@Param("followingUserIds") List<Long> followingUserIds, Pageable pageable);

    @Query("""
        SELECT new com.spring.api.API.models.DTOs.Posts.PostResponse(
            p.id,
            p.description,
            p.picture,
            p.user.username,
            COUNT(l.id),
            COUNT(c.id),
            p.datecreated
        )
        FROM Posts p
        LEFT JOIN Likes l ON l.post = p
        LEFT JOIN Comments c ON c.post = p
        WHERE p.user.id =:user_id  
        GROUP BY 
            p.id,
            p.description,
            p.picture,
            p.user.username,
            p.datecreated
        ORDER BY p.datecreated DESC
    """)
    List<PostResponse> findPosts(@Param("user_id") Long user_id);

    @Query("""
        SELECT p
        FROM Posts p
        WHERE p.user =:user_id AND p.id =:post_id
    """)
    Optional<Posts> findPostByUserAndPostId(@Param("user_id") Long user_id, Long post_id);

    @Query("""
        SELECT new com.spring.api.API.models.DTOs.Posts.PostResponse(
            p.id,
            p.description,
            p.picture,
            p.user.username,
            COUNT(l.id),
            COUNT(c.id),
            p.datecreated
        ) 
        FROM Posts p
        LEFT JOIN Likes l ON l.post = p
        LEFT JOIN Comments c ON c.post = p
        WHERE p.id =:post_id
        GROUP BY 
            p.id,
            p.description,
            p.picture,
            p.user.username,
            p.datecreated
    """)
    Optional<PostResponse> findPostResponseById(@Param("post_id") Long post_id);

    @Query("""
        SELECT new com.spring.api.API.models.DTOs.Posts.PostResponse(
            p.id,
            p.description,
            p.picture,
            p.user.username,
            COUNT(DISTINCT l.id),
            COUNT(DISTINCT c.id),
            p.datecreated
        ) 
        FROM Posts p
        LEFT JOIN Likes l ON l.post = p
        LEFT JOIN Comments c ON c.post = p
        WHERE l.user.id =:user_id    
        GROUP BY
           p.id,
           p.description,
           p.picture,
           p.user.username,
           p.datecreated,
           l.created_at
   """)
    List<PostResponse> findPostResponseByIdLikedPosts(@Param("user_id") Long user_id);
}
