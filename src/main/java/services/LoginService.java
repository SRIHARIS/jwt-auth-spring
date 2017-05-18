package services;

import models.Credentials;
import models.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LoginService {

    private final String DUMMY_UNAME = "test";
    private final String DUMMY_PWD = "test123";
    @Autowired
    private final JwtService jwtService = null;


    public Profile login(Credentials credentials) {
        Profile user = new Profile();
        try {
            //verify password salt from DB in actual implementation

            if( credentials.getUname().equals(DUMMY_UNAME) && credentials.getPwd().equals(DUMMY_PWD)) {

                user.setName("John Doe"); //Fill data fetched from datastore
                user.setJwtToken(jwtService.tokenFor(user));
                user.setSuccess(true);

            } else {
                user.setSuccess(false);
                user.setMessage("Invalid Credentials");
            }

        } catch (Exception exception) {

        }
        return user;
    }

}