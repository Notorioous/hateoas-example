package com.example.springrest.resource;

import com.example.springrest.model.User;
import com.example.springrest.rest.PostEndPoint;
import com.example.springrest.rest.UserEndPoint;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Getter
public class UserResource extends ResourceSupport {

    private final User user;


    public UserResource(final User user) {
        this.user = user;
        final int id = user.getId();
        add(linkTo(methodOn(UserEndPoint.class).getAll()).withRel("users"));
        add(linkTo(methodOn(PostEndPoint.class).getAllByUserId(id)).withRel("userPosts"));
        add(linkTo(methodOn(UserEndPoint.class).getUserById(id)).withRel("user"));
        add(linkTo(methodOn(UserEndPoint.class).delete(id)).withRel("deleteUser"));
        add(linkTo(methodOn(UserEndPoint.class).update(user)).withRel("updateUser"));
        add(linkTo(methodOn(UserEndPoint.class).save(user)).withRel("saveUser"));
    }
}
