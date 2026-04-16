package com.spring.api.API.profiles;

import com.spring.api.API.profiles.dtos.CreateProfileDTO;
import com.spring.api.API.profiles.dtos.ProfileUpdate;
import jakarta.validation.Valid;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("profiles")
public class ProfileController {

    private final ProfileService service;

    public ProfileController(ProfileService service){
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody CreateProfileDTO profileDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(profileDTO));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMyProfile(@AuthenticationPrincipal @NonNull UserDetails user){
        return ResponseEntity.status(HttpStatus.OK).body(this.service.myProfile(user.getUsername()));
    }

    @GetMapping("/me/stats")
    public ResponseEntity<?> getStats(@AuthenticationPrincipal @NonNull UserDetails user){
        return ResponseEntity.status(HttpStatus.OK).body(this.service.meProfileStats(user.getUsername()));
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchProfile(@RequestParam() String username,
                                           @NonNull @AuthenticationPrincipal UserDetails user){
        return ResponseEntity.status(HttpStatus.OK).body(this.service.searchProfile(username, user.getUsername()));
    }

    @PutMapping("/")
    public ResponseEntity<?> updateProfile(@Valid @RequestBody() ProfileUpdate data, @NonNull Authentication auth){
        return ResponseEntity.ok(this.service.updateProfile(data, auth.getName()));
    }

    @GetMapping("/{username}/posts")
    public ResponseEntity <?> searchProfilePosts(@PathVariable("username") String username,
                                                 @NonNull @AuthenticationPrincipal UserDetails user){
        return ResponseEntity.status(HttpStatus.OK).body(this.service.searchProfilePosts(username, user.getUsername()));
    }

    @GetMapping("/{username}/likes")
    public ResponseEntity <?> searchProfilePostsLiked(@PathVariable("username") String username,
                                                      @NonNull @AuthenticationPrincipal UserDetails user){
        return ResponseEntity.status(HttpStatus.OK).body(this.service.searchProfilePostsLiked(username, user.getUsername()));
    }

    @GetMapping("/{username}/stats")
    public ResponseEntity<?> getProfileStats(@PathVariable("username") String username,
                                             @NonNull @AuthenticationPrincipal UserDetails user){
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getProfileStats(username,user.getUsername()));
    }

    @PostMapping("/upload-avatar")
    public ResponseEntity<?> uploadProfilePhoto(@RequestParam("file") MultipartFile file,
                                                @NonNull @AuthenticationPrincipal UserDetails user){
        this.service.storeProfileAvatar(file, user.getUsername());
        return ResponseEntity.ok().build();
    }
}
