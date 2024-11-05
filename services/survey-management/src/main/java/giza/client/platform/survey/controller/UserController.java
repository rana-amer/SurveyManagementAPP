//package giza.client.platform.survey.controller;
//
//import giza.client.platform.survey.api.service.IUserService;
//import giza.client.platform.survey.model.dto.UserDTO;
////import giza.client.platform.survey.model.dto.Token;
//import jakarta.validation.Valid;
//import lombok.AllArgsConstructor;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.client.RestTemplate;
//
//@RestController
//@RequestMapping("/users")
//@AllArgsConstructor
//public class UserController {
//    private final IUserService userService;
//
//    @PostMapping("/register")
//    public ResponseEntity<?> createUser(@RequestHeader(HttpHeaders.AUTHORIZATION)String authorization, @Valid @RequestBody UserDTO obj) {
//////        final String auth = authorization.substring(7);
//////        RestTemplate restTemplate = new RestTemplate();
//////        String url = "http://localhost:9100/api-user/user-auth/authenticate";
//////        HttpEntity<Token> request = new HttpEntity<>(new Token(auth));
////        //        Token token = new Token();
//////        token.setToken(authorization);
//////        Boolean response = restTemplate.postForObject(url,request, Boolean.class);
//////        if (response == true){
//        Long ID = userService.createUser(obj);
//        return ResponseEntity.ok(ID);
//////    }
//////
//////        return ResponseEntity.badRequest().body("Unauthenticated");
////    }
////
////        return ResponseEntity.badRequest().body("Unauthenticated");
//    }
//
//}
