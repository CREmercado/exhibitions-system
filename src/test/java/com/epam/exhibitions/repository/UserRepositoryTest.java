package com.epam.exhibitions.repository;

import com.epam.exhibitions.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User(1L, "Jack", "Market", "Jmarkt", "12345", "Admin");
    }

    @Test
    void findHallByHallName_shouldReturnNotNull_shouldReturnHall() {
        when(userRepository.findUserByNickname(user.getNickname())).thenReturn(Optional.ofNullable(user));
        assertNotNull(userRepository.findUserByNickname(user.getNickname()));
        assertEquals(Optional.ofNullable(user), userRepository.findUserByNickname(user.getNickname()));
    }

}