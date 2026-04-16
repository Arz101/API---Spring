package com.spring.api.API.follows;

import com.spring.api.API.users.dtos.UserNode;
import com.spring.api.API.services.SocialDataStore;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class SocialRecommendationService {

    private final SocialDataStore store;
    private static final Logger log = LoggerFactory.getLogger(SocialRecommendationService.class);

    public SocialRecommendationService(SocialDataStore service){
        this.store = service;
    }

    public Set<UserNode> mutualFollows(@NonNull UserDetails user, Long userId) {
        var currentUser = new UserNode(userId, user.getUsername());
        var following = this.store.getFollowsGraph().getOrDefault(currentUser, Set.of());

        log.info("following size {}" , following.size());
        return following.stream()
                .filter(friend -> this.store.getFollowsGraph().getOrDefault(friend, Set.of())
                        .contains(currentUser)
                )
                .collect(Collectors.toSet());
    }

    public List<UserNode> suggestionsByGraph(UserDetails user, Long userId){
        var mutualFollows = this.mutualFollows(user, userId);
        Map<UserNode, Integer> relevanceCount = new HashMap<>();

        var currentUser = new UserNode(userId, user.getUsername());
        var myFollowings = this.store.getFollowsGraph().getOrDefault(currentUser, Set.of());

        for (var follow : mutualFollows){
            var following = this.store.getFollowsGraph().getOrDefault(follow, Set.of());
            for (var f : following){
                if(f.equals(currentUser) || myFollowings.contains(f)) continue;

                if (!relevanceCount.containsKey(f)){
                    relevanceCount.put(f, 1);
                } else relevanceCount.put(f, relevanceCount.get(f) + 1);
            }
        }
        return relevanceCount.entrySet().stream()
                .sorted(Map.Entry.<UserNode, Integer>comparingByValue().reversed())
                .limit(20)
                .map(Map.Entry::getKey)
                .toList();
    }

    public List<UserNode> usersWithSameInterests(UserDetails user, Long userId) {
        var currentUser = new UserNode(userId, user.getUsername());
        var myFollowings = this.store.getFollowsGraph().getOrDefault(currentUser, Set.of());
        var myTopTags = this.store.getTop5TagsByUser(currentUser);

        final int SHARED_TAG_BONUS = 30;
        final int APPEARANCE_BONUS = 60;
        final int FOLLOWS_BACK_BONUS = 150;

        Map<UserNode, Integer> candidateScores = new HashMap<>();

        myFollowings.forEach(following -> {
            var followeesOfFollow = this.store.getFollowsGraph().getOrDefault(following, Set.of());

            followeesOfFollow.forEach(candidate -> {
                if (myFollowings.contains(candidate) || candidate.equals(currentUser)) return;

                if (!candidateScores.containsKey(candidate)) {
                    var candidateTopTags = new HashSet<>(this.store.getTop5TagsByUser(candidate));
                    candidateTopTags.retainAll(myTopTags);

                    int score = candidateTopTags.size() * SHARED_TAG_BONUS;

                    boolean followsMeBack = this.store.getFollowsGraph()
                            .getOrDefault(candidate, Set.of()).contains(currentUser);
                    score += followsMeBack ? FOLLOWS_BACK_BONUS : 0;

                    candidateScores.put(candidate, score);
                } else {
                    candidateScores.merge(candidate, APPEARANCE_BONUS, Integer::sum);
                }
            });
        });

        return candidateScores.entrySet().stream()
                .sorted(Map.Entry.<UserNode, Integer>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .limit(40)
                .toList();
    }
}
