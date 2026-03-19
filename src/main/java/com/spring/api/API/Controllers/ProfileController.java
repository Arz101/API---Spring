package com.spring.api.API.Controllers;

import com.spring.api.API.models.DTOs.Profile.CreateProfileDTO;
import com.spring.api.API.services.ProfileService;
import jakarta.validation.Valid;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> get_my_profile(@NonNull Authentication authentication){
        return ResponseEntity.status(HttpStatus.OK).body(this.service.my_profile(authentication.getName()));
    }

    @GetMapping("/search")
    public ResponseEntity<?> search_profile(@RequestParam() String username, @NonNull Authentication auth){
        return ResponseEntity.status(HttpStatus.OK).body(this.service.search_profile(username, auth.getName()));
    }

    @PostMapping("/follow")
    public ResponseEntity<?> follow_user(@RequestParam() String username, @NonNull Authentication auth){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.follow_user(username, auth.getName()));
    }
}
