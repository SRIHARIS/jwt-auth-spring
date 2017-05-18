package auth;

import models.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import services.JwtService;

import java.util.Optional;


@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private final JwtService jwtService = null;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        try {
            Optional<Profile> possibleProfile = jwtService.verify((String) authentication.getCredentials());
            JwtAuthenticatedProfile authProfile = new JwtAuthenticatedProfile(possibleProfile.get());
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(authProfile,null,authProfile.getAuthorities());

            return token;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("Failed to verify token");
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthToken.class.equals(authentication);
    }
}
