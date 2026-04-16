package com.spring.api.API.comments;

import java.util.List;
import java.util.Optional;

import com.spring.api.API.comments.dtos.CommentResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ICommentsRepository extends JpaRepository<Comments, Long>{
    
    @Query("""
        SELECT c
        FROM Comments c
        WHERE c.id =:comment_id AND c.user.id =:user_id
    """)
    Optional<Comments> findByUserIdAndCommentId(@Param("user_id") Long user_id, @Param("comment_id") Long comment_id);

    @Query("""
        SELECT new com.spring.api.API.comments.dtos.CommentResponse(
            c.id,
            c.content,
            c.dateCreated,
            c.user.username,
            c.parent.id,
            c.post.id,
            COUNT(c2.id)
        )
        FROM Comments c
        LEFT JOIN Comments c2 ON c2.parent.id = c.id 
        WHERE c.post.id =:postId AND c.parent.id IS NULL
        GROUP BY c.id, c.content, c.dateCreated, c.user.username, c.parent.id, c.post.id
        ORDER BY c.dateCreated DESC
    """)
    List<CommentResponse> findCommentsByPostId(@Param("postId") Long postId);

    @Query("""
        SELECT new com.spring.api.API.comments.dtos.CommentResponse(
            c.id,
            c.content,
            c.dateCreated,
            c.user.username,
            c.parent.id,
            c.post.id,
            COUNT(c2.id)
        )
        FROM Comments c
        LEFT JOIN Comments c2 ON c2.parent.id = c.id
        WHERE c.parent.id =:commentId
        GROUP BY c.id, c.content, c.dateCreated, c.user.username, c.parent.id, c.post.id
        ORDER BY c.dateCreated DESC 
    """)
    List<CommentResponse> findRepliesByCommentId(@Param("commentId") Long commentId);

    @Query("""
        SELECT c
        FROM Comments c
        WHERE c.user.id =:user_id AND c.id=:comment_id
    """)
    Optional<Comments> findCommentByUserIdAndCommentId(@Param("user_id") Long user_id, @Param("comment_id") Long comment_id);

    @Query("""
        SELECT c
        FROM Comments c
        WHERE c.id =:comment_id
    """)
    Optional<Comments> findCommentById(@Param("comment_id") Long comment_id);

    @Query("""
        SELECT new com.spring.api.API.comments.dtos.CommentResponse(
            c.id,
            c.content,
            c.dateCreated,
            c.user.username,
            c.parent.id,
            c.post.id,
            COUNT(c2.id)
        )
        FROM Comments c
        LEFT JOIN Comments c2 ON c2.parent.id = c.id 
        WHERE c.post.id =:postId
        GROUP BY c.id, c.content, c.dateCreated, c.user.username, c.parent.id, c.post.id
    """)
    List<CommentResponse> findAllCommentsByPostId(@Param("postId") Long postId);
}

