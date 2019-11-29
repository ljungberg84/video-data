package se.complexjava.videostreamingapi.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.complexjava.videostreamingapi.model.UserModel;
import se.complexjava.videostreamingapi.model.VideoModel;
import se.complexjava.videostreamingapi.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity<UserModel> createUser ( @Valid @RequestBody UserModel user ) throws Exception{

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
    }


    @GetMapping("/{userId}")
    public ResponseEntity<UserModel> getUser (@PathVariable Long userId) throws Exception {

        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(userId));
    }


    @GetMapping
    public ResponseEntity<Iterable<UserModel>> getUsers () {

        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsers());
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(@PathVariable Long userId){

        userService.deleteUser(userId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @PutMapping("/{userId}")
    public ResponseEntity updateUser(@PathVariable long userId, @Valid @RequestBody UserModel user) throws Exception{

        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(user, userId));
    }


    @PostMapping("/{userId}/videos")
    public ResponseEntity<VideoModel> addVideo (@PathVariable long userId, @Valid @RequestBody VideoModel video ) throws Exception{

        return null;
    }


    @GetMapping("/{userId}/videos")
    public ResponseEntity<VideoModel> getVideos ( @PathVariable long userId, @Valid @RequestBody VideoModel video ) throws Exception{

        return null;
    }
}
