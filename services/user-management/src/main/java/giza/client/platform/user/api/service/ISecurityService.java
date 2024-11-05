package giza.client.platform.user.api.service;

import giza.client.platform.user.model.dto.AuthenticationRequest;
//import giza.client.platform.user.model.dto.RegisterRequest;
import giza.client.platform.user.model.dto.RegisterRequest;
import giza.client.platform.user.model.dto.UserDTO;
import giza.client.platform.user.model.vto.AuthenticationResponse;

public interface ISecurityService {
    Long register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);

}
