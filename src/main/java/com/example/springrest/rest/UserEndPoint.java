package com.example.springrest.rest;

import com.example.springrest.model.User;
import com.example.springrest.repository.UserRepository;
import com.example.springrest.resource.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
public class UserEndPoint {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user/add")
    public ResponseEntity save(@RequestBody User user){

        if(userRepository.findByEmail(user.getEmail()) != null) {
            final URI uri = MvcUriComponentsBuilder
                    .fromController(getClass())
                    .path("/{id}")
                    .buildAndExpand(user.getId()).toUri();
            return ResponseEntity
                    .created(uri)
                    .body(new UserResource(user));
        }

        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/all")
    public ResponseEntity <Resources<UserResource>> getAll(){
        List<UserResource> collect = userRepository.findAll().stream().map(UserResource::new).collect(Collectors.toList());
        final Resources<UserResource> resources = new Resources<>(collect);
        final String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
        resources.add(new Link(uriString,"self"));
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity getUserById(@PathVariable("id") int id){
        Optional<User> byId = userRepository.findById(id);
        if(byId.isPresent()){
            UserResource userResource =new UserResource(byId.get());

            return ResponseEntity.ok(userResource);
        }
        return ResponseEntity
                .notFound()
                .build();
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity delete(@PathVariable("id") int id){
        Optional<User> byId = userRepository.findById(id);
        if(byId.isPresent()){
            userRepository.deleteById(id);
            return ResponseEntity
                    .ok()
                    .build();
        }
        return ResponseEntity
                .notFound()
                .build();
    }

    @PutMapping("/user/update")
    public ResponseEntity update(@RequestBody User user){
       if(userRepository.findById(user.getId()).isPresent()){
           userRepository.save(user);
           final URI uri = ServletUriComponentsBuilder
                   .fromCurrentRequest()
                   .build().toUri();
           return ResponseEntity
                   .created(uri)
                   .body(new UserResource(user));
       }
       return ResponseEntity
               .notFound()
               .build();
    }

}
