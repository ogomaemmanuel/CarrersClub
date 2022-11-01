package com.careerclub.careerclub.Controllers;

import com.careerclub.careerclub.DTOs.UserCreationRequest;
import com.careerclub.careerclub.DTOs.UserUpdateRequest;
import com.careerclub.careerclub.Entities.User;
import com.careerclub.careerclub.ResourceAssembler.UserResourceAssembler;
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

@Tag(name = "Users Controller")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final UsernameValidator usernameValidator;
    private final EmailValidator emailValidator;
    private final UserResourceAssembler userResourceAssembler;


    public UserController(UserService userService, UsernameValidator usernameValidator, EmailValidator emailValidator, UserResourceAssembler userResourceAssembler) {
        this.userService = userService;
        this.usernameValidator = usernameValidator;
        this.emailValidator = emailValidator;
        this.userResourceAssembler = userResourceAssembler;
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

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserCreationRequest userCreationRequest, BindingResult errors){
        usernameValidator.validate(userCreationRequest,errors);
        emailValidator.validate(userCreationRequest,errors);
        if(!errors.hasErrors()){
        var user = userService.createUser(userCreationRequest);
        var userWithLinks = userResourceAssembler.toModel(user);
        return ResponseEntity.status(201).body("Account created successfully");
        }
        return ResponseEntity.badRequest().body(ErrorConverter.convert(errors));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id,@Valid @RequestBody UserUpdateRequest userUpdateRequest){
        var user = userService.updateUser(id,userUpdateRequest);
        return ResponseEntity.of(user);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HashMap<Object,Object>> deleteUser(@PathVariable Long id){
        var validate = userService.deleteUser(id);
        return ResponseEntity.ok(validate);
    }

}
