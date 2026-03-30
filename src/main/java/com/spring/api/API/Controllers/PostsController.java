package com.spring.api.API.Controllers;

import org.jspecify.annotations.NonNull;
import org.springframework.web.bind.annotation.*;
import com.spring.api.API.models.DTOs.Posts.CreatePostDTO;
import com.spring.api.API.models.DTOs.Posts.UpdatePostDTO;
import com.spring.api.API.services.PostsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;


@RestController
@RequestMapping("/posts")
public class PostsController {
    
    private final PostsService service;
    public PostsController(PostsService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<?> postMethodName(@Valid @RequestBody CreatePostDTO posts, @NonNull Authentication auth) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.create(posts, auth.getName()));
    }
    
    @GetMapping("/me")
    public ResponseEntity<?> getMyPosts(@NonNull Authentication auth) {
        return ResponseEntity.ok(this.service.getMyPosts(auth.getName()));
    }

    @GetMapping("/feed")
    public ResponseEntity<?> getFeed(@NonNull Authentication auth) {
        return ResponseEntity.ok(this.service.feed(auth.getName()));    
    }

    @GetMapping("/")
    public ResponseEntity<?> get_user_posts(@RequestParam("username") String username, @NonNull Authentication auth){
        return ResponseEntity.ok(this.service.get_user_posts(username, auth.getName()));
    }

    @PostMapping("/like/{post_id}")
    public ResponseEntity<?> likePost(@PathVariable long post_id, @NonNull Authentication auth) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.setLike(post_id, auth.getName()));
    }

    @PatchMapping("/{post_id}")
    public ResponseEntity<?> updatePost(@Valid @PathVariable long post_id, @RequestBody() UpdatePostDTO data, @NonNull Authentication auth){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(this.service.updatePost(data, post_id, auth.getName()));
    }

    @DeleteMapping("/{post_id}")
    public ResponseEntity<?> deletePost(@PathVariable long post_id, @NonNull Authentication auth){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(this.service.deletePost(post_id, auth.getName()));
    }

    @GetMapping("{post_id}")
    public ResponseEntity<?> findPostByIdEntity(@PathVariable long post_id, @NonNull Authentication auth) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(this.service.findPostById(post_id, auth.getName()));
    }

    @GetMapping("/{post_id}/hashtags")
    public ResponseEntity<?> getPostsWithHashTags(@PathVariable("post_id") long post_id, Authentication auth){
        return ResponseEntity.status(HttpStatus.OK).body(this.service.getPostsWithHashTags(post_id, auth.getName()));
    }
}
