package com.spring.api.API.services;

import com.spring.api.API.Repositories.*;
import com.spring.api.API.models.DTOs.Posts.CreatePostDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class PostsServiceTest {

    @Autowired private IPostsRepository repository;
    @Autowired private IFollowsRepository followsRepository;
    @Autowired private IUserRepository userRepository;
    @Autowired private IProfileRepository profileRepository;
    @Autowired private IHashTagsRepository hashTagsRepository;
    @Autowired private StorageService storage;
    @Autowired private FeedService feedService;
    @Autowired private CacheAsyncHelper cacheAsyncHelper;
    @Autowired private ILikeRepository likeRepository;
    @Autowired private IPostViewedRepository postViewedRepository;
    @Autowired private SocialDataStore store;

    @BeforeEach
    void setUp() {
        store = new SocialDataStore(
                followsRepository,
                repository,
                hashTagsRepository,
                likeRepository,
                postViewedRepository
        );
        store.initPosts();
        ReflectionTestUtils.setField(postsService, "store", store);
    }

    @AfterEach
    void clean(){
        store.clean();
    }

    @Autowired
    private PostsService postsService;

    @Test
    @Transactional
    public void createNewPost(){
        Long userId = 33168L;
        Set<String> hashtags = Set.of("java", "test");
        var newPost = new CreatePostDTO("Test", "default", hashtags);
        String username = "arz";
        var result = postsService.create(newPost, username);
        assertThat(result).isNotNull();
    }

    @Test
    public void getAllPostsByUser(){
        String username = "arz";

        var result = this.postsService.getMyPosts(username);
        assertThat(result)
                .isNotEmpty()
                .allSatisfy(post ->
                        assertThat(post.post().username()).isEqualTo(username)
                );
    }
}
