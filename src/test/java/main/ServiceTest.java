package main;

import models.Credentials;
import models.Profile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import services.JwtService;
import services.LoginService;


/**
 * Testing the login and jwt services
 */

@RunWith(SpringRunner.class)
@SpringBootTest

public class ServiceTest {

    private final String TEST_UNAME = "test";
    private final String TEST_PWD = "test123";
    private final String TEST_NAME = "John Doe";

    @Autowired
    private final LoginService loginService = null;

    @Autowired
    private final JwtService jwtService = null;

    @Test
    public void loginTest() throws Exception {
        Credentials pair = new Credentials();
        pair.setUname(TEST_UNAME);
        pair.setPwd(TEST_PWD);
        Profile profile = loginService.login(pair);
        assert(profile.isSuccess());
    }

    @Test
    public void JWTProcessTest() throws Exception {

        Credentials pair = new Credentials();
        pair.setUname(TEST_UNAME);
        pair.setPwd(TEST_PWD);

        //Get the token
        Profile profile = loginService.login(pair);
        jwtService.tokenFor(profile);

        //Parse the token
        Profile afterDecode = jwtService.verify(profile.getJwtToken()).get();

        //Verify the user details
        assert(afterDecode.getName().equals(TEST_NAME));
    }
}
