package com.spring.api.API.services;

import com.github.benmanes.caffeine.cache.Cache;
import com.spring.api.API.models.DTOs.Posts.*;
import com.spring.api.API.models.DTOs.User.UserNode;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@EnableAsync
public class FeedService {
    private static final Logger log = LoggerFactory.getLogger(FeedService.class);
    private final Cache<Long, Set<PostResponseWithHashtags>> feed;
    private final SocialDataStore socialService;

    private Map<String, Map<String, Integer>> hashtagGraph = new HashMap<>();
    private Map<Long, Set<UserNode>> postsLikesByUsers = new HashMap<>();
    private Map<String, Set<PostsObject>> postsGroupedByTags = new HashMap<>();

    private Map<Long, Integer> postIdRankNote = new HashMap<>();

    public FeedService(Cache<Long, Set<PostResponseWithHashtags>> userRankingCache,
                       SocialDataStore socialService){
        this.socialService = socialService;
        this.feed = userRankingCache;

    }

    @PostConstruct
    private void load(){
        this.postsLikesByUsers = this.socialService.getPostsLikesByUsers();
        this.hashtagGraph = this.socialService.getHashtagGraph();
        this.postsGroupedByTags = this.socialService.getPostsGroupedByTags();
    }

    public Set<PostResponseWithHashtags> posts(String username, Long userId, int page, int size){
        return this.feed.get(userId, u -> this.createFeed(username, userId, page, size));
    }

    public List<?> getPostsByMap(String username){
        var posts = this.socialService.getPostsByUsers().getOrDefault(username, new ArrayList<>());

        return posts.stream().map(post -> {
            var p = new PostResponse(
                    post.id, post.description, post.picture,
                    post.username, post.likes, post.comments,
                    post.datecreated
            );
            return new PostResponseWithHashtags(p,
                    this.socialService.getHashtagsByPosts().getOrDefault(post.id, new HashSet<>()));
        }).collect(Collectors.toList());
    }

    public List<?> getMostHashOccurrencesByHash(String hashtag){
        var map = this.hashtagGraph.getOrDefault(hashtag, new HashMap<>());
        return map.entrySet().stream()
                .sorted(Map.Entry.<String,Integer>comparingByValue().reversed())
                .limit(5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public Set<Long> postsILiked(String username, Long userId){
        var currentUser = new UserNode(userId, username);

        var posts = this.postsLikesByUsers.entrySet().stream()
                .filter(key -> key.getValue().contains(currentUser))
                .collect(Collectors.toSet());

        return posts.stream().map(p -> p.getKey())
                .collect(Collectors.toSet());
    }

    public List<String> tagsLikedByUser(String username, Long userId){
        var postsLiked = this.postsILiked(username, userId);
        Map <String, Integer> tags = new HashMap<>();

        for (var id : postsLiked){
            var tagsByPost = this.socialService.getHashtagsByPosts().getOrDefault(id, new HashSet<>());

            for (var tag : tagsByPost){
                if(!tags.containsKey(tag)){
                    tags.put(tag, 1);
                } else {
                    tags.put(tag, tags.get(tag) + 1);
                }
            }
        }

        return tags.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public Set<PostResponseWithHashtags> createFeed (String username, Long userId, int page, int size){
        var tags = tagsLikedByUser(username, userId);
        var tagsOccurrences = tags.parallelStream()
                .flatMap(tag -> {
                    var map = this.hashtagGraph.getOrDefault(tag, Map.of());
                    return map.entrySet().stream()
                            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                            .limit(3)
                            .map(Map.Entry::getKey)
                            .collect(Collectors.toSet()).stream();
                })
                .collect(Collectors.toSet());

        var popularPostsByTags = tagsOccurrences.stream()
                .flatMap(tag -> this.postsGroupedByTags.getOrDefault(tag, new HashSet<>())
                        .stream())
                .map(to ->
                    new PostResponseWithHashtags(
                            to.toPostResponse(),
                            this.socialService.getHashtagsByPosts().getOrDefault(to.getId(), new HashSet<>())
                    )
                ).skip((long) page * size)
                .limit(size)
                .collect(Collectors.toSet());

        return popularPostsByTags;
    }

    public List<?> rankPosts(){
        List<Long> ids = this.socialService.getPostIds();
        return List.of();
    }
}
