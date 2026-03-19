package com.spring.api.API.security;

import com.spring.api.API.Repositories.IUserRepository;
import com.spring.api.API.models.User;
import com.spring.api.API.security.Exceptions.AccountException;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceHandler implements UserDetailsService {

    private final IUserRepository repository;

    public UserDetailsServiceHandler(IUserRepository repository){
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("INVALID CREDENTIALS"));

        if (!user.getStatus().equals("active"))
            throw new AccountException("Account is not active, check your email for confirmation");
        
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                new ArrayList<>()
        );
    }
}
