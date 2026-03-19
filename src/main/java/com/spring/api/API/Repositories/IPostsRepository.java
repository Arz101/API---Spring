package com.spring.api.API.Repositories;

import com.spring.api.API.models.Posts;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IPostsRepository extends JpaRepository<Posts, Long> {
    Posts findById(long id);
}
