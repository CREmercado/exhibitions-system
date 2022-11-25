package com.epam.exhibitions.service.impl;

import com.epam.exhibitions.entity.User;
import com.epam.exhibitions.repository.UserRepository;
import com.epam.exhibitions.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> optUser = userRepository.findById(id);
        return optUser.orElse(null);
    }

    @Override
    public User getUserByNickname(String nickname) {
        Optional<User> optUser = userRepository.findUserByNickname(nickname);
        return optUser.orElse(null);
    }

    @Override
    public void addUser(User user) throws Exception {
        if(user != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        } else {
            throw new Exception("Not a valid user to save");
        }
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void updateUser(User user) throws Exception {
        if(user != null) {
            userRepository.save(user);
        } else {
            throw new Exception("Not a valid user to update");
        }
    }
}
