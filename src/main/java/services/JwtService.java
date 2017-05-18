package services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import java.security.Key;
import models.Profile;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import static java.time.ZoneOffset.UTC;

@Component
public class JwtService {
    private static final String ISSUER = "com.srihari.jwt";
    private static String keyPhrase = "StrongHell"; //Read from configuration file
    private byte[] key = Base64.encodeBase64(keyPhrase.getBytes());

    @SuppressWarnings("unused")
    public JwtService() {

    }

    public String tokenFor(Profile minimalProfile) throws IOException, URISyntaxException {

        return Jwts.builder()
                .setSubject(minimalProfile.name)
                .setIssuer(ISSUER)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    public Optional<Profile> verify(String token) throws IOException, URISyntaxException {
        Jws<Claims> claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
        Profile user = new Profile();
        user.setName(claims.getBody().getSubject().toString());
        return Optional.of(user);
    }
}