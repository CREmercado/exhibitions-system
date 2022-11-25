package com.epam.exhibitions.controller;

import com.epam.exhibitions.entity.User;
import com.epam.exhibitions.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserServiceImpl userServiceImpl;
    @InjectMocks
    private UserController userController;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User(1L, "Jack", "Market", "Jmark", "12345", "Admin");
    }

    @Test
    void getUsers_shouldReturnNotNull_shouldReturnUsers() {
        when(userServiceImpl.listUsers()).thenReturn(Collections.singletonList(user));
        assertNotNull(userController.getUsers());
        assertEquals(Collections.singletonList(user), userController.getUsers());
    }

    @Test
    void getUserById_shouldReturnNotNull_shouldReturnUser() {
        when(userServiceImpl.getUserById(user.getUserId())).thenReturn(user);
        assertNotNull(userController.getUserById(user.getUserId()));
        assertEquals(user, userController.getUserById(user.getUserId()));
    }

    @Test
    void addUser_shouldNotChangeUser_shouldThrowException() throws Exception {
        // test for checking the not changing of main object when not adding new objects
        User userSpy = Mockito.spy(user);
        doNothing().when(userServiceImpl).addUser(userSpy);
        userController.addUser(userSpy);
        assertEquals(user.getUserId(), userSpy.getUserId());

        // test for checking the trowing fo exeption
        UserController userControllerMock =  Mockito.mock(UserController.class);
        doThrow(Exception.class).when(userControllerMock).addUser(isNull());
        assertThrows(Exception.class, () -> userControllerMock.addUser(isNull()));
    }

    @Test
    void deleteUser_shouldNotChangeUser() {
        // test for checking the not changing of main object when not deleting
        User userSpy = Mockito.spy(user);
        doNothing().when(userServiceImpl).deleteUserById(user.getUserId());
        userController.deleteUserById(userSpy.getUserId());
        assertEquals(userSpy.getUserId(), user.getUserId());
    }

    @Test
    void updateUserById_shouldNotChangeUser_shouldThrowException() throws Exception {
        // test for checking the not changing of main object when not updating the object
        User userSpy = Mockito.spy(user);
        userServiceImpl.updateUser(userSpy);
        assertEquals(user.getUserId(), userSpy.getUserId());

        // test for checking the trowing for exception
        UserController userControllerMock =  Mockito.mock(UserController.class);
        doThrow(Exception.class).when(userControllerMock).updateUserById(eq(0L), isNull());
        assertThrows(Exception.class, () -> userControllerMock.updateUserById(eq(0L), isNull()));
    }
}