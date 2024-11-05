package giza.client.platform.user.controller;

import giza.client.platform.user.core.service.PrivilegService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/privileges")
@RequiredArgsConstructor
public class PrivilegeController {
    private final PrivilegService privilegService;
    @Secured({"admin","manager"})
    @PutMapping("/roles/{roleID}/user/{userID}")
    public ResponseEntity<?> addExtraRole(@PathVariable Long userID, @PathVariable Long roleID){
        privilegService.assignRole(userID,roleID);
        return ResponseEntity.noContent().build();
    }
    @Secured({"admin","manager"})
    @PutMapping("/roles/user/{userID}")
    public ResponseEntity<?> removeAllRole(@PathVariable Long userID){
        privilegService.removeRole(userID);
        return ResponseEntity.noContent().build();
    }
    @Secured("admin")
    @GetMapping("/roles/{roleID}/users")
    public ResponseEntity<?> getUsersHasRole(@PathVariable Long roleID){
        return new  ResponseEntity<>(privilegService.getUsersWithRole(roleID), HttpStatus.OK);
    }
}
