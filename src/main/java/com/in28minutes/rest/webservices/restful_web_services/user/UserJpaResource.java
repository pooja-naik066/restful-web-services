package com.in28minutes.rest.webservices.restful_web_services.user;

import com.in28minutes.rest.webservices.restful_web_services.jpa.PostRepository;
import com.in28minutes.rest.webservices.restful_web_services.jpa.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/jpa")
public class UserJpaResource {
    private UserRepository userRepository;
    private PostRepository postRepository;


    @Autowired
    public UserJpaResource(UserRepository userRepository,PostRepository postRepository){
        this.userRepository=userRepository;
        this.postRepository=postRepository;
    }

  @GetMapping("/users")
    public List<User> retrievAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUserById(@PathVariable Integer id){
            Optional<User> user=userRepository.findById(id);
        if(user.isEmpty()){
            throw new UserNotFoundException("id "+id);
        }
        EntityModel<User> entityModel=EntityModel.of(user.get());
        WebMvcLinkBuilder link=linkTo(methodOn(this.getClass()).retrievAllUsers());
        entityModel.add(link.withRel("all-users"));
        return entityModel;

    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        User savedUser=userRepository.save(user);
        URI location= ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();

    }


    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Integer id){
        userRepository.deleteById(id);
    }

    @GetMapping("/users/{id}/posts")
    public List<Post> retrievePostsForUser(@PathVariable Integer id){
        Optional<User> user=userRepository.findById(id);
        if(user.isEmpty()){
            throw new UserNotFoundException("id "+id);
        }
        return user.get().getPosts();
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Post> createPostForUser( @PathVariable Integer id,@Valid @RequestBody Post post){

        Optional<User> user=userRepository.findById(id);
        if(user.isEmpty()){
            throw new UserNotFoundException("id "+id);
        }
        post.setUser(user.get());
        Post savedPost=postRepository.save(post);
        URI location= ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId())
                .toUri();
       return ResponseEntity.created(location).build();

    }
}
