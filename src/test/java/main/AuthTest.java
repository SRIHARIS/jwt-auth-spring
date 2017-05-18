package main;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import models.Credentials;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthTest {

    private final String TEST_UNAME = "test";
    private final String TEST_PWD = "test123";
    private final String TEST_NAME = "John Doe";
    private final String POSITIVE_TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJXZWIgTWFzdGVyIiwiaXNzIjoiY29tLnNyaWhhcmkuand0In0.EA_XYZiCX7xRi2wHZB4fe0hH1b1Xf_mFT1uhB50OacBeSydeSRLpjQ2DrjE55-s2Gcem4jdaz_3ex10LqaZucw";
    private final String INVALID_TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJXZWIgTWFzdGVyIiwiZXhwIjoxNDk0OTc3Mzg2LCJpc3MiOiJjb20uc3JpaGFyaS5qd3QifQ.QnA3QLHuzi005URPOdO0cVn58uMpN3OEpHiC2uRKOiA99qB1z0W-CSisdHHlB9CF1ZB4aBHK4nsTWFaHDxUA";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void homeWithoutToken() throws Exception {

        this.mockMvc.perform(get("/home")).andDo(print()).andExpect(status().is(403));
    }

    @Test
    public void homeWithInvalidToken() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/home");


        request.header("Authorization","Bearer " + INVALID_TOKEN);

        this.mockMvc.perform(request).andDo(print()).andExpect(status().is(403));
    }

    @Test
    public void homeWithValidToken() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/home");

        request.header("Authorization","Bearer " + POSITIVE_TOKEN);

        this.mockMvc.perform(request).andDo(print()).andExpect(status().is(200));
    }

    @Test
    public void loginWithToken() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/login");

        //also the keys do not have to be unique, two keys of the same value will both get added
        request.header("Authorization","Bearer " + POSITIVE_TOKEN);
        request.contentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(request).andDo(print()).andExpect(status().is(400));
    }

    @Test
    public void loginWithoutCredentials() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/login");

        request.contentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(request).andDo(print()).andExpect(status().is(400));
    }

    @Test
    public void loginWithCredentials() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/login");

        request.contentType(MediaType.APPLICATION_JSON);
        Credentials cred = new Credentials();

        cred.setPwd(TEST_PWD);
        cred.setUname(TEST_UNAME);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(cred);

        request.content(requestJson);

        this.mockMvc.perform(request).andDo(print()).andExpect(status().is(200));
    }

}
