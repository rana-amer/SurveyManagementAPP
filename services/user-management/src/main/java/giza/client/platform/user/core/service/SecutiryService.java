package giza.client.platform.user.core.service;


import giza.client.platform.user.api.service.ISecurityService;
import giza.client.platform.user.core.repository.UserRepository;
import giza.client.platform.user.exceptions.UserException;
import giza.client.platform.user.model.dto.AuthenticationRequest;
//import giza.client.platform.user.model.dto.RegisterRequest;
import giza.client.platform.user.model.dto.RegisterRequest;
import giza.client.platform.user.model.dto.UserDTO;
import giza.client.platform.user.model.entity.User;
import giza.client.platform.user.model.vto.AuthenticationResponse;
import giza.notification.sender.model.dto.NotificationDTO;
import giza.notification.sender.service.SendNotification;
import giza.user.sync.model.dto.UserSyncDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SecutiryService implements ISecurityService {
    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper mapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final JmsTemplate template;
    private final SendNotification sendNotification;
    @Override
    public Long register(RegisterRequest request) {
        if (repo.findByUsername(request.getUsername()) != null){
            throw new UserException("Username already existed");
        }
        if (repo.findByEmail(request.getEmail()) != null){
            throw new UserException("Email already existed");
        }
//        request.setCreatedOn(new Date());
        User user = User.builder()
                .fullName(request.getFullName())
                .username(request.getUsername())
                .email(request.getEmail())
                .userPassword(passwordEncoder.encode(request.getPassword()))
                .createdOn(new Date())
                .gender(request.getGender())
                .mobile(request.getMobile())
                .isActive(request.getIsActive())
                .build();
//        User user1 = modelMapper.map(user, User.class);
        repo.save(user);
        UserSyncDTO userSyncDTO = mapper.map(user, UserSyncDTO.class);
//        mapper.map(user,userSyncDTO);
        template.convertAndSend("giza.survey.save.user",userSyncDTO);
        template.convertAndSend("giza.ntf.save.user",userSyncDTO);
//        Map<String, String> m = new HashMap<String, String>();
//
//        // Initialize frequency table from command line
//        m.put("deadline","9/25/2023");
//        NotificationDTO notificationDTO = new NotificationDTO(
//                1,
//                1,
//                new Date(),
//                1,
//                Arrays.asList(2),
//                m
//);
        return user.getId();
//        var jwtToken = jwtService.generateToken(user);
//        return AuthenticationResponse.builder().token(jwtToken).build();
    }
    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        if (repo.findByUsername(request.getUsername()) == null) {
            throw new UserException("Username not existed");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword())
        );
        User user = repo.findByUsername(request.getUsername());

//        Map<String,Object> extraClaims = new HashMap<>();
//        extraClaims.put("privileges", List.of("Role-Manager","Role-Admin","Group-Guest","Permission-Get-Survey","Permission-Deactivate-User"));
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();

    }
}
