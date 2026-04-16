package com.spring.api.API.Repositories;

import com.spring.api.API.tags.IHashTagsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DataJpaTest
@Transactional
public class PostRepositoryTest {

    @Autowired
    private IHashTagsRepository hashTagsRepository;
    private static final Logger log = LoggerFactory.getLogger(PostRepositoryTest.class);
    
    @Test
    public void getAllHashtagsByUserIdQuery() {

    }
}
