package com.careerclub.careerclub.Service;

import com.careerclub.careerclub.Advice.BadRequestException;
import com.careerclub.careerclub.Advice.DuplicateException;
import com.careerclub.careerclub.Advice.RecordNotFoundException;
import com.careerclub.careerclub.DTOs.UserCreationRequest;
import com.careerclub.careerclub.DTOs.UserUpdateRequest;
import com.careerclub.careerclub.Entities.User;
import com.careerclub.careerclub.Repositories.UserRepository;
import com.careerclub.careerclub.Utils.EmailValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


    public Page<User> getAllUsers(Pageable pageable){
        return userRepository.findAll(pageable);
    }

    public Optional<User> getSingleUserById(Long id){
        var user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new RecordNotFoundException("User with the given id doesn't exist.");
        }
        return user;
    }

    public User createUser(UserCreationRequest userCreationRequest){
        var emailCheck = userRepository.findByEmail(userCreationRequest.getEmail());
        var usernameCheck = userRepository.findByUsername(userCreationRequest.getUsername());
        if(emailCheck.isPresent()){
            throw new DuplicateException("The given email address already exists.");
        }
        if(usernameCheck.isPresent()){
            throw new DuplicateException("The given username already exists.");
        }
        var emailIsValid = validate(userCreationRequest.getEmail());
        if(emailIsValid){
            var user = new User();
            user.setUsername(userCreationRequest.getUsername());
            user.setPassword(userCreationRequest.getPassword());
            user.setEmail(userCreationRequest.getEmail());
            userRepository.save(user);
            return user;
        }else {
            throw new BadRequestException("Email format provided is invalid.");
        }

    }

    public Optional<User> updateUser(Long id, UserUpdateRequest userUpdateRequest){
        var user = userRepository.findById(id);
        user.ifPresentOrElse(u->{
            if(userUpdateRequest.getPhoneNumber()!=null){
                u.setPhoneNumber(userUpdateRequest.getPhoneNumber());
            }else if (userUpdateRequest.getProfession()!=null){
                u.setProfession(userUpdateRequest.getProfession());
            }else if(userUpdateRequest.getBio()!=null){
                u.setBio(userUpdateRequest.getBio());
            }
            userRepository.save(u);
        },()->{
            throw new RecordNotFoundException("User with the given id  doesn't exist");
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
            throw new RecordNotFoundException("User with the given id  doesn't exist");
        });

        return validate;
    }

}
