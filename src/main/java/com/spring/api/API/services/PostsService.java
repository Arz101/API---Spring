package com.spring.api.API.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.spring.api.API.Repositories.IFollowsRepository;
import com.spring.api.API.Repositories.ILikeRepository;
import com.spring.api.API.Repositories.IPostsRepository;
import com.spring.api.API.Repositories.IProfileRepository;
import com.spring.api.API.Repositories.IUserRepository;
import com.spring.api.API.models.Likes;
import com.spring.api.API.models.Posts;
import com.spring.api.API.models.Profiles;
import com.spring.api.API.models.User;
import com.spring.api.API.models.DTOs.Posts.CreatePostDTO;
import com.spring.api.API.models.DTOs.Posts.PostResponse;
import com.spring.api.API.security.Exceptions.PostNotFoundException;
import com.spring.api.API.security.Exceptions.UserNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import jakarta.transaction.Transactional;

@Service
public class PostsService {
    
    private final IPostsRepository repository;
    private final IProfileRepository profileRepository;
    private final ILikeRepository likeRepository;
    private final IFollowsRepository followsRepository;
    private final IUserRepository userRepository;

    public PostsService(IPostsRepository repository,
        IProfileRepository profileRepository,
        ILikeRepository likeRepository,
        IFollowsRepository followsRepository,
        IUserRepository userRepository
    ) {
        this.repository = repository;
        this.profileRepository = profileRepository;
        this.likeRepository = likeRepository;
        this.followsRepository = followsRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public PostResponse create(CreatePostDTO dto, String username) {
        Profiles profile = this.profileRepository.findProfilesByUserUsername(username)
            .orElseThrow(() -> new UserNotFoundException("User not found"));
        
        Posts post = this.repository.save(new Posts(
            dto,
            profile
        ));

        return new PostResponse(
            post.getId(),
            post.getDescription(),
            post.getPicture(),
            username,
            0L,
            post.getDatecreated()
        );
    }

    public List<PostResponse> getPostsByUsername(String username) {
        List<Posts> posts = this.repository.findByUsername(username);
        
        return posts.stream().map(post -> new PostResponse(
            post.getId(),
            post.getDescription(),
            post.getPicture(),
            username,
            this.likeRepository.countLikesByPostId(post.getId()),
            post.getDatecreated()
        )).toList();
    }

    public List<PostResponse> feed(String username) {
        List<Long> userIds = this.followsRepository.findFollowedUserIdsByFollowerUsername(username);
        Pageable pageable = PageRequest.of(0, 100);
        return this.repository.findFeed(userIds, pageable);
    }

    @Transactional
    public String setLike(Long post_id, String username){
        Posts post = this.repository.findById(post_id)
            .orElseThrow(() -> new PostNotFoundException("Post not found"));

        User user = this.userRepository.findByUsername(username)
            .orElseThrow(() -> new UserNotFoundException("Something went wrong!"));

        Optional<Likes> like = this.likeRepository.findLikeByUserAndPost(user.getId(), post_id);
        if (like.isEmpty()){
            this.likeRepository.save(new Likes(user, post));
            return "Liked";
        }
        
        this.likeRepository.delete(like.get());
        return "Unliked";
    }
}
