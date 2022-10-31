package com.careerclub.careerclub.ResourceAssembler;

import com.careerclub.careerclub.Controllers.UserController;
import com.careerclub.careerclub.DTOs.UserUpdateRequest;
import com.careerclub.careerclub.Entities.User;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserResourceAssembler implements RepresentationModelAssembler<User, EntityModel<User>> {

    @Override
    public EntityModel<User> toModel(User entity) {
        EntityModel<User> user = EntityModel.of(entity);
        user.add(linkTo(methodOn(UserController.class).getUserById(entity.getId())).withSelfRel());
        user.add(linkTo(methodOn(UserController.class).deleteUser(entity.getId())).withSelfRel());
        return user;
    }
}
