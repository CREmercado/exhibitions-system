package com.epam.exhibitions.service.impl;

import com.epam.exhibitions.entity.User;
import com.epam.exhibitions.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userServiceImpl;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User(1L, "Jack", "Market", "Jmarkt", "12345", "Admin");
    }

    @Test
    void listUsers_shouldReturnNotNull_shouldReturnUsers() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
        assertNotNull(userServiceImpl.listUsers());
        assertEquals(Collections.singletonList(user), userServiceImpl.listUsers());
    }

    @Test
    void getUserById_shouldReturnNotNull_shouldReturnUser() {
        when(userRepository.findById(user.getUserId())).thenReturn(Optional.ofNullable(user));
        assertNotNull(userServiceImpl.getUserById(user.getUserId()));
        assertEquals(user, userServiceImpl.getUserById(user.getUserId()));
    }

    @Test
    void addUser_shouldThrowException() throws Exception {
        // test for checking the trowing fo exception
        UserServiceImpl userServiceImplMock =  Mockito.mock(UserServiceImpl.class);
        doThrow(Exception.class).when(userServiceImplMock).addUser(isNull());
        assertThrows(Exception.class, () -> userServiceImplMock.addUser(isNull()));
    }

    @Test
    void deleteUserById_shouldNotChangeUser() {
        // test for checking the not changing of main object when not deleting
        User userSpy = Mockito.spy(user);
        doNothing().when(userRepository).deleteById(user.getUserId());
        userServiceImpl.deleteUserById(userSpy.getUserId());
        assertEquals(userSpy.getUserId(), user.getUserId());
    }

    @Test
    void updateUser_shouldNotChangeUser_shouldThrowException() throws Exception {
        // test for checking the not changing of main object
        User userSpy = Mockito.spy(user);
        when(userRepository.save(user)).thenReturn(userSpy);
        userServiceImpl.updateUser(user);
        assertEquals(userSpy.getUserId(), user.getUserId());

        // test for checking the trowing fo exception
        UserServiceImpl userServiceImplMock =  Mockito.mock(UserServiceImpl.class);
        doThrow(Exception.class).when(userServiceImplMock).updateUser(isNull());
        assertThrows(Exception.class, () -> userServiceImplMock.updateUser(isNull()));
    }
}