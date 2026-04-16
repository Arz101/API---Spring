package com.spring.api.API.services;

import com.spring.api.API.follows.IFollowsRepository;
import com.spring.api.API.likes.ILikeRepository;
import com.spring.api.API.posts.IPostsRepository;
import com.spring.api.API.posts.postviewed.IPostViewedRepository;
import com.spring.api.API.tags.IHashTagsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SocialDataStoreTest {
    @Mock private IFollowsRepository followsRepository;
    @Mock private IPostsRepository postsRepository;
    @Mock private IHashTagsRepository hashTagsRepository;
    @Mock private ILikeRepository likeRepository;
    @Mock private IPostViewedRepository postViewedRepository;

    @InjectMocks
    private SocialDataStore store;

    @Test
    void addNewPostsTest(){

    }
}
