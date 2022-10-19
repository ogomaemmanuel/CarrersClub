package com.careerclub.careerclub.Service;

import com.careerclub.careerclub.Advice.RecordNotFoundException;
import com.careerclub.careerclub.DTOs.UserCreationRequest;
import com.careerclub.careerclub.DTOs.UserUpdateRequest;
import com.careerclub.careerclub.Entities.User;
import com.careerclub.careerclub.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.careerclub.careerclub.Utils.EmailValidator.validate;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> getSingleUserById(Long id){
        var user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new RecordNotFoundException("User with id "+id+" doesn't exist.");
        }
        return user;
    }

    public User createUser(UserCreationRequest userCreationRequest){
        var emailIsValid = validate(userCreationRequest.getEmail());
        if(emailIsValid){
            var user = new User();
            user.setUsername(userCreationRequest.getUsername());
            user.setPassword(userCreationRequest.getPassword());
            user.setEmail(userCreationRequest.getEmail());
            userRepository.save(user);
            return user;
        }else {
            throw new RecordNotFoundException("Email format provided is invalid.");
        }

    }

    public Optional<User> updateUser(Long id, UserUpdateRequest userUpdateRequest){
        var user = userRepository.findById(id);
        user.ifPresentOrElse(u->{
            u.setFullName(userUpdateRequest.getFullName());
            u.setPhoneNumber(userUpdateRequest.getPhoneNumber());
            u.setProfession(userUpdateRequest.getProfession());
            u.setBio(userUpdateRequest.getBio());
            userRepository.save(u);
        },()->{
            throw new RecordNotFoundException("User with id "+id+" doesn't exist 🚫");
        });

        return user;
    }

    public HashMap<Object,Object> deleteUser(Long id){
        var validate = new HashMap<>();
        var user = userRepository.findById(id);
        user.ifPresentOrElse(u->{
            userRepository.delete(u);
            validate.put("message","User deleted successfully ✅");
        },()->{
            throw new RecordNotFoundException("User with id "+id+" doesn't exist 🚫");
        });

        return validate;
    }

}
