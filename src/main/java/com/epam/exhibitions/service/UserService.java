package com.epam.exhibitions.service;

import com.epam.exhibitions.entity.User;

import java.util.List;

public interface UserService {

    List<User> listUsers();

    User getUserById(Long id);

    User getUserByNickname(String nickname);

    void addUser(User User) throws Exception;

    void deleteUserById(Long id);

    void updateUser(User User) throws Exception;

}
