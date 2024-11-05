package giza.client.platform.user.config;

import giza.client.platform.user.core.service.JwtService;
import giza.client.platform.user.model.dto.UserTokenDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthToken {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public Boolean authenticate(UserTokenDTO token) {
        try {
            final String authHeader = token.getToken();
//        final String jwt;
            final String username;
//        if (authHeader == null || !authHeader.startsWith("Bearer")) {
//            return false;
//        }
//        jwt = authHeader.substring(17);
//        if (jwtService.extractUsername(authHeader).isEmpty()){return false;}


            username = jwtService.extractUsername(authHeader);
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (jwtService.isTokenValid(authHeader, userDetails)) {
                return true;
//                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                        userDetails,
//                        null,
//                        userDetails.getAuthorities()
//                );

            }
            return false;
//        }
//        return false;
        } catch (Exception catchAllException) {
            return false;
        }
    }

}



