package com.example.springrest.rest;

import com.example.springrest.model.Post;
import com.example.springrest.model.User;
import com.example.springrest.repository.PostRepository;
import com.example.springrest.repository.UserRepository;
import com.example.springrest.resource.UserResource;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PostEndPoint {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/post/all")
    public ResponseEntity getAll(){
        return ResponseEntity.ok(postRepository.findAll());
    }

    @PostMapping("/post/add")
    public ResponseEntity add(@RequestBody Post post){
        Optional<Post> byId = postRepository.findById(post.getId());
        if(!byId.isPresent()){
            postRepository.save(post);
            return ResponseEntity.ok(post);
        }
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .build();
    }

    @GetMapping("/getPostsByUserId/{id}")
    public ResponseEntity getAllByUserId(@PathVariable("id") int id){
        User one = userRepository.getOne(id);
        List<Post> posts = postRepository.findByUserId(id);
        Link link = ControllerLinkBuilder.linkTo(UserResource.class).slash(one.getId()).withSelfRel();
        return ResponseEntity.ok(posts);
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity delete(@PathVariable("id") int id){
        Optional<Post> byId = postRepository.findById(id);
        if(byId.isPresent()){
            postRepository.deleteById(id);
            return ResponseEntity
                    .ok()
                    .build();
        }
        return ResponseEntity
                .notFound()
                .build();
    }

    @PutMapping("/post/update")
    public ResponseEntity update(@RequestBody Post post){
        Optional<Post> byId = postRepository.findById(post.getId());
        if(byId.isPresent()){
            postRepository.save(post);
            return ResponseEntity
                    .ok(post);
        }
        return ResponseEntity
                .notFound()
                .build();
    }
}
