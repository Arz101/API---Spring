package com.spring.api.API.services;

import com.spring.api.API.Repositories.IPostViewedRepository;
import com.spring.api.API.models.DTOs.Comments.UpdateCommentDTO;
import com.spring.api.API.models.PostViewed;
import com.spring.api.API.security.Exceptions.CommentsActionsUnauthorized;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.spring.api.API.Repositories.ICommentsRepository;
import com.spring.api.API.Repositories.IPostsRepository;
import com.spring.api.API.Repositories.IUserRepository;
import com.spring.api.API.models.Comments;
import com.spring.api.API.models.Posts;
import com.spring.api.API.models.User;
import com.spring.api.API.models.DTOs.Comments.CommentsCreateDTO;
import com.spring.api.API.models.DTOs.Comments.CommentsResponse;
import com.spring.api.API.security.Exceptions.CommentsNotFoundExceptions;
import com.spring.api.API.security.Exceptions.PostNotFoundException;
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
    public CommentsResponse create(CommentsCreateDTO comment, String username){
        User curr = this.userRepository.findByUsername(username)
            .orElseThrow(() -> new UserNotFoundException("Something went wrong"));
        
        Posts post = this.postsRepository.findById(comment.post_id())
            .orElseThrow(() -> new PostNotFoundException("Post not found"));
        
        Comments new_comment = this.repository.save(new Comments(
            post,
            curr,
            comment.content()
        ));

        if (!post.getUser().getId().equals(curr.getId())) {
            this.postViewedRepository.save(new PostViewed(post, curr));
        }

        return new CommentsResponse(
            new_comment.getId(),
            new_comment.getPost().getId(),
            new_comment.getUser().getUsername(),
            new_comment.getContent(),
            new_comment.getDatecreated()
        );
    }

    @Transactional
    public String deleteComment(Long comment_id, String username){
        Long user_id = this.userRepository.getIdByUsername(username)
            .orElseThrow(() -> new UserNotFoundException("User not found"));

        Comments comment = this.repository.findByUserIdAndCommentId(user_id, comment_id)
            .orElseThrow(() -> new CommentsNotFoundExceptions("Something went wrong!"));
        this.repository.delete(comment);
        return "Successfully";
    }

    @Transactional(readOnly = true)
    public List<CommentsResponse> findCommentsByPostId(Long post_id){
        return this.repository.findCommentsByPostId(post_id);
    }

    @Transactional
    public CommentsResponse updateComment(UpdateCommentDTO data, String username){
        Long user_id = this.userRepository.getIdByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Comments comment = this.repository.findCommentByUserIdAndCommentId(user_id, data.id())
                .orElseThrow(() -> new CommentsActionsUnauthorized("Unauthorized Actions"));

        comment.setContent(data.content());
        Comments comment_mod = this.repository.save(comment);

        return new CommentsResponse(
                comment_mod.getId(),
                comment_mod.getPost().getId(),
                comment_mod.getUser().getUsername(),
                comment_mod.getContent(),
                comment_mod.getDatecreated()
        );
    }
}
