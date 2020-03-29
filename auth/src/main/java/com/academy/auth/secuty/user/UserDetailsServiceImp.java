package com.academy.auth.secuty.user;

import com.academy.core.model.ApplicationUser;
import com.academy.core.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
        if(applicationUser == null) {
            throw new UsernameNotFoundException(String.format("Application user '%s' not found",username));
        }
        return new CustomUserDetails(applicationUser);
    }

}
