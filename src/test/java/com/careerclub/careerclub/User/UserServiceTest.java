package com.careerclub.careerclub.User;

import com.careerclub.careerclub.DTOs.UserCreationRequest;
import com.careerclub.careerclub.DTOs.UserUpdateRequest;
import com.careerclub.careerclub.Entities.User;
import com.careerclub.careerclub.Repositories.UserRepository;
import com.careerclub.careerclub.Service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserService userService;




    @Test
    @DisplayName("Testing getting a single user using the user id")
    public void test_single_user(){
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
        userService.getSingleUserById(1L);
        verify(userRepository).findById(1L);
    }

    @Test
    @DisplayName("Testing User Creation")
    public void test_user_creation(){
        var user = new UserCreationRequest();
        user.setUsername("Testing");
        user.setEmail("testing@testing.com");
        user.setPassword("Testing");
        when(userRepository.save(any(User.class))).thenReturn(new User());
        userService.createUser(user);
        verify(userRepository).save(any(User.class));

    }

    @Test
    @DisplayName("Testing user update")
    public void test_user_update(){
        var user = new UserUpdateRequest();
        user.setPhoneNumber("0712345678");
        user.setProfession("Testing Profession");
        user.setBio("Testing bio");
        when(userRepository.save(any(User.class))).thenReturn(new User());
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
        userService.updateUser(1L,user);
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Testing user delete")
    public void test_user_delete(){
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
        doNothing().when(userRepository).delete(any(User.class));
        userService.deleteUser(1L);
        verify(userRepository).delete(any(User.class));
    }

}
