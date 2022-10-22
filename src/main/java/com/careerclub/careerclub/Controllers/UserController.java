package com.careerclub.careerclub.Controllers;

import com.careerclub.careerclub.DTOs.UserCreationRequest;
import com.careerclub.careerclub.DTOs.UserUpdateRequest;
import com.careerclub.careerclub.Entities.User;
import com.careerclub.careerclub.Service.UserService;
import com.careerclub.careerclub.Utils.EmailValidator;
import com.careerclub.careerclub.Utils.ErrorConverter;
import com.careerclub.careerclub.Utils.UsernameValidator;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@Tag(name = "Users Controller")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final UsernameValidator usernameValidator;
    private final EmailValidator emailValidator;


    public UserController(UserService userService, UsernameValidator usernameValidator, EmailValidator emailValidator) {
        this.userService = userService;
        this.usernameValidator = usernameValidator;
        this.emailValidator = emailValidator;
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers(Pageable pageable){
        var users = userService.getAllUsers(pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        var user = userService.getSingleUserById(id);
        return ResponseEntity.of(user);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UserCreationRequest userCreationRequest, BindingResult errors){
        usernameValidator.validate(userCreationRequest,errors);
        emailValidator.validate(userCreationRequest,errors);
        if(!errors.hasErrors()){
        var user = userService.createUser(userCreationRequest);
        return ResponseEntity.ok(user);
        }
        return ResponseEntity.badRequest().body(ErrorConverter.convert(errors));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id,@Valid @RequestBody UserUpdateRequest userUpdateRequest){
        var user = userService.updateUser(id,userUpdateRequest);
        return ResponseEntity.of(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HashMap<Object,Object>> deleteUser(@PathVariable Long id){
        var validate = userService.deleteUser(id);
        return ResponseEntity.ok(validate);
    }

}
