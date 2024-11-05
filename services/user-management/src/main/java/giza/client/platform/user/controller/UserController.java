package giza.client.platform.user.controller;

import giza.client.platform.user.api.service.IUserService;
import giza.client.platform.user.model.UserListFilter;
import giza.client.platform.user.model.dto.UserDTO;
import giza.client.platform.user.model.entity.User;
import giza.client.platform.user.model.vto.UserVTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Secured("admin")
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final IUserService userService;


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
        return new  ResponseEntity<>(userService.getUsers(userListFilter), HttpStatus.OK);
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
