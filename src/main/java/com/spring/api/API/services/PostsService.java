package com.spring.api.API.services;

import java.util.List;
import java.util.Map;
import com.spring.api.API.security.Exceptions.PostsActionsUnauthorized;
import org.springframework.stereotype.Service;
import com.spring.api.API.Repositories.IFollowsRepository;
import com.spring.api.API.Repositories.ILikeRepository;
import com.spring.api.API.Repositories.IPostViewedRepository;
import com.spring.api.API.Repositories.IPostsRepository;
import com.spring.api.API.Repositories.IUserRepository;
import com.spring.api.API.models.Likes;
import com.spring.api.API.models.PostViewed;
import com.spring.api.API.models.Posts;
import com.spring.api.API.models.User;
import com.spring.api.API.models.DTOs.Posts.CreatePostDTO;
import com.spring.api.API.models.DTOs.Posts.PostResponse;
import com.spring.api.API.models.DTOs.Posts.UpdatePostDTO;
import com.spring.api.API.security.Exceptions.PostNotFoundException;
import com.spring.api.API.security.Exceptions.UserNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostsService {
    private final IPostsRepository repository;
    private final ILikeRepository likeRepository;
    private final IFollowsRepository followsRepository;
    private final IUserRepository userRepository;
    private final IPostViewedRepository postViewedRepository;

    public PostsService(IPostsRepository repository,
        ILikeRepository likeRepository,
        IFollowsRepository followsRepository,
        IUserRepository userRepository,
        IPostViewedRepository postViewedRepository
    ) {
        this.repository = repository;
        this.likeRepository = likeRepository;
        this.followsRepository = followsRepository;
        this.userRepository = userRepository;
        this.postViewedRepository = postViewedRepository;
    }

    @Transactional
    public PostResponse create(CreatePostDTO dto, String username) {
        Long user_id = this.userRepository.getIdByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Posts post = this.repository.save(new Posts(
            dto, this.userRepository.getReferenceById(user_id)
        ));

        return new PostResponse(
            post.getId(),
            post.getDescription(),
            post.getPicture(),
            username,
            0L,
            0L,
            post.getDatecreated()
        );
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username) {
        Long user_id = this.userRepository.getIdByUsername(username)
            .orElseThrow(() -> new UserNotFoundException("Something went wrong"));

        return this.repository.findPosts(user_id);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> feed(String username) {
        List<Long> userIds = this.followsRepository.findFollowedUserIdsByFollowerUsername(username);
        Pageable pageable = PageRequest.of(0, 20);
        return this.repository.findFeed(userIds, pageable);
    }

    @Transactional
    public Map<String,String> setLike(Long post_id, String username){
        Posts post = this.repository.getReferenceById(post_id);

        Long user_id = this.userRepository.getIdByUsername(username)
            .orElseThrow(() -> new UserNotFoundException("Something went wrong!"));

        User user = this.userRepository.getReferenceById(user_id);

        if(!this.likeRepository.findLikeByUserAndPost(user_id, post_id)) {
            this.likeRepository.save(new Likes(user, post));
        }

        if (!post.getUser().getId().equals(user_id) &&
            !this.postViewedRepository.alreadyViewed(user_id, post_id)
        ) {
            this.postViewedRepository.save(new PostViewed(post, user));
            return Map.of("message", "Liked");
        }

        return Map.of("message", "Already Liked");
    }

    @Transactional
    public PostResponse updatePost(UpdatePostDTO data, Long post_id, String username){
        Long user_id = this.userRepository.getIdByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Not found"));

        Posts post = this.repository.findPostByUserAndPostId(user_id, post_id)
                .orElseThrow(() -> new PostsActionsUnauthorized("Unauthorized Action"));

        post.setDescription(data.getDescription());
        
        this.repository.save(post);
        return this.repository.findPostResponseById(post.getId())
                .orElseThrow(() -> new PostNotFoundException("Something went wrong"));
    }

    @Transactional
    public String deletePost(Long post_id, String username){
        Long user_id = this.userRepository.getIdByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Not found"));

        Posts post = this.repository.findPostByUserAndPostId(user_id, post_id)
            .orElseThrow(() -> new PostsActionsUnauthorized("Unauthorized Action"));
        
        try{
            this.likeRepository.deleteByPostId(post_id);

            this.repository.delete(post);
            return "Successfully!";
        }
        catch(RuntimeException e){
            return "Something went wrong!";
        }
    }

    @Transactional(readOnly = true)
    public PostResponse findPostById(Long post_id){
        return this.repository.findPostResponseById(post_id)
            .orElseThrow(() -> new PostNotFoundException("Not found"));
    }
}
