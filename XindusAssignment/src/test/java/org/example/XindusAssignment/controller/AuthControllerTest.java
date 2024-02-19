package org.example.XindusAssignment.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.XindusAssignment.model.dto.JwtRequest;
import org.example.XindusAssignment.model.User;
import org.example.XindusAssignment.security.JwtHelper;
import org.example.XindusAssignment.service.CustomUserDetailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {AuthController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class AuthControllerTest {
    @Autowired
    private AuthController authController;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private CustomUserDetailService customUserDetailService;

    @MockBean
    private JwtHelper jwtHelper;

    /**
     * Method under test: {@link AuthController#exceptionHandler()}
     */
    @Test
    void testExceptionHandler() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange, Act and Assert
        assertEquals("Credentials Invalid !", (new AuthController()).exceptionHandler());
    }

    /**
     * Method under test: {@link AuthController#login(JwtRequest)}
     */
    @Test
    void testLogin() throws Exception {
        // Arrange
        when(jwtHelper.generateToken(Mockito.<UserDetails>any())).thenReturn("ABC123");
        when(customUserDetailService.loadUserByUsername(Mockito.<String>any())).thenReturn(new User());
        when(authenticationManager.authenticate(Mockito.<Authentication>any()))
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));

        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUserEmail("jane.doe@example.org");
        jwtRequest.setUserPassword("iloveyou");
        String content = (new ObjectMapper()).writeValueAsString(jwtRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(authController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"token\":\"ABC123\",\"username\":null}"));
    }

    /**
     * Method under test: {@link AuthController#login(JwtRequest)}
     */
    @Test
    void testLogin2() throws Exception {
        // Arrange
        when(jwtHelper.generateToken(Mockito.<UserDetails>any())).thenReturn("ABC123");
        when(customUserDetailService.loadUserByUsername(Mockito.<String>any())).thenReturn(new User());
        when(authenticationManager.authenticate(Mockito.<Authentication>any()))
                .thenThrow(new BadCredentialsException("passed"));

        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUserEmail("jane.doe@example.org");
        jwtRequest.setUserPassword("iloveyou");
        String content = (new ObjectMapper()).writeValueAsString(jwtRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(authController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Credentials Invalid !"));
    }
}
