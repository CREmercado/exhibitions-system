package com.epam.exhibitions.service.impl;

import com.epam.exhibitions.entity.User;
import com.epam.exhibitions.entity.UserDetailsImpl;
import com.epam.exhibitions.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> optionalUser = userRepository.findUserByNickname(username);
        return optionalUser.map(UserDetailsImpl::new)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

    }
}
