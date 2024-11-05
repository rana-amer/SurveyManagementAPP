package giza.client.platform.user.controller;


import giza.client.platform.user.config.AuthToken;
import giza.client.platform.user.core.service.SecutiryService;
import giza.client.platform.user.model.dto.AuthenticationRequest;
//import giza.client.platform.user.model.dto.RegisterRequest;
import giza.client.platform.user.model.dto.RegisterRequest;
import giza.client.platform.user.model.dto.UserDTO;
import giza.client.platform.user.model.dto.UserTokenDTO;
import giza.client.platform.user.model.vto.AuthenticationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/security")
@RequiredArgsConstructor
public class SecurityController {
    private final SecutiryService service;
    private final AuthToken authToken;
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request){
        Long ID = service.register(request);
        return ResponseEntity.ok(ID);
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(service.authenticate(request));
    }
    @PostMapping("/token")
    public Boolean authToken(@RequestBody UserTokenDTO token) {
        return authToken.authenticate(token);
    }
}
