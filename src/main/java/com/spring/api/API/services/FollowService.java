package com.spring.api.API.services;

import com.spring.api.API.Repositories.IFollowsRepository;
import com.spring.api.API.Repositories.IProfileRepository;
import com.spring.api.API.Repositories.IUserRepository;
import com.spring.api.API.models.Follows;
import com.spring.api.API.models.Profiles;
import com.spring.api.API.models.User;
import com.spring.api.API.security.Exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class FollowService {

    private final IFollowsRepository repository;
    private final IUserRepository userRepository;
    private final IProfileRepository profileRepository;

    public FollowService(
            IFollowsRepository repository,
            IUserRepository userRepository,
            IProfileRepository profileRepository
    ){
        this.repository = repository;
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
    }

    @Transactional
    public Map<String, String> follow_user(String target, String currentUser){
        User userTarget = this.userRepository.findByUsername(target)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Long user_id = this.userRepository.getIdByUsername(currentUser)
                .orElseThrow(() -> new UserNotFoundException("Something went wrong!"));

        User user = this.userRepository.getReferenceById(user_id);

        String status = "active";
        String request = "Now following user";

        if (this.profileRepository.isPrivate(userTarget.getId())){
            status = "pending";
            request = "Follow request sent";
        }

        this.repository.save(new Follows(
                        user,
                        userTarget,
                        status
                )
        );
        return Map.of("message", request);
    }

    public List<String> getFollowerUsernames(String username){
        return this.repository.findFollowersUsernames(username);
    }

    public List<String> getFollowedUsernames(String username){
        return this.repository.findFollowedUsernames(username);
    }
}
