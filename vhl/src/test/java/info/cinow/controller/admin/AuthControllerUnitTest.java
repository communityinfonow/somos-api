package info.cinow.controller.admin;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import info.cinow.authentication.JwtUtils;
import info.cinow.authentication.LoginRequest;
import info.cinow.service.AuthService;

/**
 * AuthControllerUnitTest
 */
@RunWith(SpringRunner.class)
@WebMvcTest(AuthController.class)
public class AuthControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    JwtUtils jwtUtils;

    @MockBean
    AuthService authService;

    Authentication auth;

    private LoginRequest loginRequest;

    @Before
    public void setup() {
        loginRequest.setUsername("joemoran231@gmail.com");
        loginRequest.setPassword("fakePassword");
        Mockito.when(authService.authorize(loginRequest)).thenReturn(auth);
    }

    @Test
    public void login() throws Exception {
        this.mockMvc.perform(post("/login", this.loginRequest)).andExpect(status().isNotAcceptable());
    }
}