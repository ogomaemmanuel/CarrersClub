package com.careerclub.careerclub.Controllers;

import com.careerclub.careerclub.DTOs.UserCreationRequest;
import com.careerclub.careerclub.DTOs.UserUpdateRequest;
import com.careerclub.careerclub.Entities.User;
import com.careerclub.careerclub.Service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@Tag(name = "Users Controller")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        var users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        var user = userService.getSingleUserById(id);
        return ResponseEntity.of(user);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody UserCreationRequest userCreationRequest){
        var user = userService.createUser(userCreationRequest);
        return ResponseEntity.ok(user);
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
