package com.spring.api.API.services;

import com.spring.api.API.Repositories.IFollowsRepository;
import com.spring.api.API.Repositories.IHashTagsRepository;
import com.spring.api.API.Repositories.ILikeRepository;
import com.spring.api.API.Repositories.IPostsRepository;
import com.spring.api.API.models.DTOs.Posts.HashtagsDTO;
import com.spring.api.API.models.DTOs.Posts.PostResponse;
import com.spring.api.API.models.DTOs.Posts.PostsObject;
import com.spring.api.API.models.DTOs.User.UserNode;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Component
public class SocialDataStore {

    private final IFollowsRepository followsRepository;
    private final IPostsRepository postsRepository;
    private final IHashTagsRepository hashTagsRepository;
    private final ILikeRepository likeRepository;

    private final Map<Long, PostsObject> postsById = new HashMap<>();
    private final Map<String, List<PostsObject>> postsByUsers = new HashMap<>();
    private final Map<Long, Set<String>> hashtagsByPosts = new HashMap<>();
    private final Map<String, Set<PostsObject>> postsGroupedByTags = new HashMap<>();
    private final Map<String, Map<String, Integer>> hashtagGraph = new HashMap<>();
    private final Map<Long, Set<UserNode>> postsLikesByUsers = new HashMap<>();
    private final Map<UserNode, Set<UserNode>> followsGraph = new HashMap<>();

    private List<Long> postIds = new ArrayList<>();

    private static final Logger log = LoggerFactory.getLogger(SocialDataStore.class);

    public SocialDataStore(IFollowsRepository followsRepository,
                           IPostsRepository postsRepository,
                           IHashTagsRepository hashTagsRepository,
                           ILikeRepository likeRepository){
        this.followsRepository = followsRepository;
        this.postsRepository = postsRepository;
        this.hashTagsRepository = hashTagsRepository;
        this.likeRepository = likeRepository;
    }

    @PostConstruct
    public void build(){
        this.buildFollowsGraph();
        this.initPosts();
    }

    @Transactional(readOnly = true)
    public void buildFollowsGraph(){
        var follows = this.followsRepository.getAll();
        for (var f : follows){
            this.followsGraph.computeIfAbsent(new UserNode(f.followerId(), f.followerUsername()), k -> new HashSet<>())
                    .add(new UserNode(f.followedId(), f.followedUsername()));
        }

        log.info("Graph charged!");
        log.info("Graph Size: {}",followsGraph.size());
    }

    @Transactional(readOnly = true)
    public void initPosts(){
        var posts = this.postsRepository.getAllPosts();
        var hashtags = this.hashTagsRepository.getAllHashtagsByPosts();
        var likes = this.likeRepository.allLikes();

        this.postIds = posts.stream()
                .map(p -> p.id())
                .collect(Collectors.toList());

        for (var post : posts){
            this.postsByUsers.computeIfAbsent(post.username(), u -> new ArrayList<>())
                    .add(new PostsObject(post));

            if(!this.postsById.containsKey(post.id())){
                this.postsById.put(post.id(), new PostsObject(post));
            }
        }

        for(var hash : hashtags){
            this.hashtagsByPosts.computeIfAbsent(hash.post_id(), h -> new HashSet<>())
                    .add(hash.name());

            this.postsGroupedByTags.computeIfAbsent(hash.name(), h -> new HashSet<>())
                    .add(this.postsById.get(hash.post_id()));
        }

        for(var like : likes){
            this.postsLikesByUsers.computeIfAbsent(like.postId(), l -> new HashSet<>())
                    .add(new UserNode(like.userId(), like.username()));
        }


        this.LoadHashtagsGraph(posts, hashtags);
    }

    private void LoadHashtagsGraph(@NonNull List<PostResponse> posts, @NonNull List<HashtagsDTO> hashtags){
        Set<Long> postsIds = posts.stream()
                .map(p -> p.id())
                .collect(Collectors.toSet());

        for(var hash: hashtags){
            if (!this.hashtagGraph.containsKey(hash.name())){
                this.hashtagGraph.put(hash.name(), new HashMap<>());
            }
        }

        log.info("total posts: {}", postsIds.size());
        log.info("Graph nodes: {}", this.hashtagGraph.size());

        for (var id : postsIds){
            var hashSet = this.hashtagsByPosts.getOrDefault(id, new HashSet<>());
            if(hashSet.isEmpty() || hashSet.size() == 1) continue;

            var tags = new ArrayList<>(hashSet);

            for (int i = 0; i < tags.size(); i++) {
                for (int j = i + 1; j < tags.size(); j++) {
                    var a = tags.get(i);
                    var b = tags.get(j);

                    this.hashtagGraph
                            .computeIfAbsent(a, k -> new HashMap<>())
                            .merge(b, 1, Integer::sum);
                    this.hashtagGraph
                            .computeIfAbsent(b, k -> new HashMap<>())
                            .merge(a, 1 , Integer::sum);
                }
            }
        }
    }

}
