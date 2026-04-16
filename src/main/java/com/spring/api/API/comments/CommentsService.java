package com.spring.api.API.comments;

import com.spring.api.API.comments.dtos.CommentReplyCreate;
import com.spring.api.API.comments.dtos.CommentResponse;
import com.spring.api.API.comments.dtos.CommentsCreateDTO;
import com.spring.api.API.comments.dtos.UpdateCommentDTO;
import com.spring.api.API.posts.postviewed.IPostViewedRepository;
import com.spring.api.API.posts.postviewed.PostViewed.PostViewed;
import com.spring.api.API.security.Exceptions.CommentsActionsUnauthorized;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.spring.api.API.posts.IPostsRepository;
import com.spring.api.API.users.IUserRepository;
import com.spring.api.API.security.Exceptions.CommentsNotFoundExceptions;
import com.spring.api.API.security.Exceptions.UserNotFoundException;

import java.util.List;

@Service
public class CommentsService {
    
    private final ICommentsRepository repository;
    private final IUserRepository userRepository;
    private final IPostsRepository postsRepository;
    private final IPostViewedRepository postViewedRepository;

    public CommentsService(
        ICommentsRepository repository,
        IUserRepository userRepository,
        IPostsRepository postsRepository,
        IPostViewedRepository postViewedRepository
    ){
        this.repository = repository;
        this.userRepository = userRepository;
        this.postsRepository = postsRepository;
        this.postViewedRepository = postViewedRepository;
    }

    @Transactional
    public CommentResponse create(@NonNull CommentsCreateDTO comment, String username){
        var userId = this.userRepository.getIdByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Something went wrong"));

        var curr = this.userRepository.getReferenceById(userId);
        
        var post = this.postsRepository.getReferenceById(comment.postId());
        
        Comments newComment = this.repository.save(new Comments(
            post,
            curr,
            comment.content()
        ));

        if (!post.getUser().getId().equals(curr.getId())) {
            this.postViewedRepository.save(new PostViewed(curr, post));
        }

        return new CommentResponse(
            newComment.getId(),
            newComment.getContent(),
            newComment.getDateCreated(),
            newComment.getUser().getUsername(),
            null,
            newComment.getPost().getId(), 0L
        );
    }

    @Transactional
    public String deleteComment(Long commentId, String username){
        Long userId = this.userRepository.getIdByUsername(username)
            .orElseThrow(() -> new UserNotFoundException("User not found"));

        Comments comment = this.repository.findByUserIdAndCommentId(userId, commentId)
            .orElseThrow(() -> new CommentsNotFoundExceptions("Something went wrong!"));
        this.repository.delete(comment);
        return "Successfully";
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> findCommentsByPostId(Long postId){
        return this.repository.findCommentsByPostId(postId);
    }

    @Transactional
    public CommentResponse updateComment(@NonNull UpdateCommentDTO data, String username){
        Long userId = this.userRepository.getIdByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Comments comment = this.repository.findCommentByUserIdAndCommentId(userId, data.id())
                .orElseThrow(() -> new CommentsActionsUnauthorized("Unauthorized Actions"));

        comment.setContent(data.content());
        Comments newComment = this.repository.save(comment);

        return new CommentResponse(
                newComment.getId(),
                newComment.getContent(),
                newComment.getDateCreated(),
                newComment.getUser().getUsername(),
                null,
                newComment.getPost().getId(),
                0L
        );
    }

    @Transactional
    public CommentResponse replayComment(@NonNull CommentReplyCreate newComment,
                                          String username,
                                          Long parentId){

        var currentUser = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Something went wrong"));

        var commentToRep = this.repository.getReferenceById(parentId);
        var post = this.postsRepository.getReferenceById(commentToRep.getPost().getId());

        var comment = this.repository.save(new Comments(
                post,
                currentUser,
                newComment.content(),
                commentToRep
        ));

        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getDateCreated(),
                comment.getUser().getUsername(),
                commentToRep.getId(),
                comment.getPost().getId(),
                0L
        );
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> findReplies(Long commentId){
        return  this.repository.findRepliesByCommentId(commentId);
    }

    @Transactional(readOnly = true)
    public List<?> getCommentsByPostId(Long postId){
        var comments = this.repository.findAllCommentsByPostId(postId);
        return new CommentsHierarchy().buildHierarchy(comments);
    }
}

