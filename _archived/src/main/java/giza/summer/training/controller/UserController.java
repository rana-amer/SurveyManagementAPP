package giza.summer.training.controller;

import giza.summer.training.api.service.IUserService;
import giza.summer.training.model.UserListFilter;
import giza.summer.training.model.dto.UserDTO;
import giza.summer.training.model.vto.UserVTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity createUser(@Valid @RequestBody UserDTO user) {
        Long id = userService.createUser(user);
        return ResponseEntity.ok(id);
    }

//    @GetMapping()
//    public ResponseEntity<?> getAllUsers() {
//        List<User> users = userService.getAllDetails();
//        return ResponseEntity.ok(users);
//    }

    @GetMapping("/Id/{Id}")
    public ResponseEntity<?> getUserById(@PathVariable Long Id) {
        UserVTO user = userService.getDetailsById(Id);
        return ResponseEntity.ok(user);

    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        UserVTO user = userService.getDetailsByUsername(username);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/Id/{id}")
    public ResponseEntity<?> updateUserById(@Valid @RequestBody UserDTO user, @PathVariable Long id) {
        userService.updateUserById(user, id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/username/{username}")
    public ResponseEntity<?> updateUserByUsername(@Valid @RequestBody UserDTO user, @PathVariable String username) {
        userService.updateUserByUsername(user, username);
        return ResponseEntity.noContent().build();
    }
    @GetMapping
    public ResponseEntity<?> getUsers(UserListFilter userListFilter){
        return new  ResponseEntity<>(userService.getUsers(userListFilter),HttpStatus.OK);
    }

    @DeleteMapping("/Id/{Id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long Id) {
        userService.deleteUserById(Id);
        return ResponseEntity.noContent().build();
    }
//    @DeleteMapping()
//    public ResponseEntity<?> deleteAllDetails() {
//        return userService.deleteAllDetails();
//    }


    @DeleteMapping("/username/{username}")
    public ResponseEntity<?> deleteUserByUsername(@PathVariable String username) {
        userService.deleteUserByUsername(username);
        return ResponseEntity.noContent().build();
    }


}
