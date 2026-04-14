package com.spring.api.API.services;

import com.github.benmanes.caffeine.cache.Cache;
import com.spring.api.API.models.DTOs.Posts.PostResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CacheAsyncHelper {

    private final SocialDataStore socialDataStore;
    private final Cache<Long, List<PostResponse>> feed;
    private final FeedService feedService;
    private final Set<Long> processing = ConcurrentHashMap.newKeySet();
    private static final Logger log = LoggerFactory.getLogger(CacheAsyncHelper.class);

    public CacheAsyncHelper(SocialDataStore socialDataStore,
                            Cache<Long, List<PostResponse>> feed,
                            FeedService feedService) {
        this.socialDataStore = socialDataStore;
        this.feed = feed;
        this.feedService = feedService;
    }

    @Async("taskExecutor")
    @EventListener(ApplicationReadyEvent.class)
    public void processLoader(){
        long start = System.currentTimeMillis();
        CompletableFuture<Void> t1 = this.socialDataStore.buildFollowsGraph();
        CompletableFuture<Void> t2 = this.socialDataStore.initPosts();

        CompletableFuture.allOf(t1,t2).join();

        CompletableFuture<Void> t3 = this.socialDataStore.tagLikedPerUser();
        CompletableFuture<Void> t4 = this.socialDataStore.loadPostsViewed();

        CompletableFuture.allOf(t3,t4).join();
        long end = (System.currentTimeMillis() - start);
        log.info("PROCESS LOAD COMPLETE SUCCESSFULLY TIME: {}", end);
    }

    @Async
    public void createFeedAsync(String username, Long userId){
        var posts = feed.getIfPresent(userId);

        if(!processing.add(userId)) return;

        if (posts != null) {
            log.info("FEED ALREADY CREATED!!!");
            return;
        }
        try {
            var result = this.feedService.createFeed(username, userId);
            this.feed.put(userId, result);
        } finally {
            processing.remove(userId);
        }
    }

    public List<PostResponse> posts(Long userId, int page, int size){
        var posts = feed.getIfPresent(userId);

        if(posts == null){
            return List.of();
        }
        return posts.stream().skip((long) page * size)
                .limit(size).toList();
    }
}
