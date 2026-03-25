package com.spring.api.API.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.api.API.models.Likes;
import com.spring.api.API.models.User;

public interface ILikeRepository extends JpaRepository<Likes, Long> {
    @Query("""
        SELECT COUNT(*) 
        FROM Likes l 
        WHERE l.post.id = :postId
    """)
    Long countLikesByPostId(long postId);    

    @Query("""
        SELECT l
        FROM Likes l
        WHERE l.user.id = :userId AND l.post.id = :postId            
    """)
    Optional<Likes> findLikeByUserAndPost(Long userId, Long postId);
}
