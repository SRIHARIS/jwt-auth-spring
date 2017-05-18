package controllers;

import auth.JwtAuthToken;
import auth.JwtAuthenticatedProfile;
import models.Credentials;
import models.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import services.LoginService;

import javax.servlet.http.HttpServletResponse;

import java.security.Principal;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class Controller {

    @Autowired
    private final LoginService loginService = null;


    @RequestMapping("/home")
    public String home() {
        return "Hello Jon Doe";
    }

    @RequestMapping(path = "/login",
            method = POST,
            produces = APPLICATION_JSON_VALUE)
    public Profile login(@RequestBody Credentials body,
                         HttpServletResponse response) {

        return this.loginService.login(body);
    }
}
