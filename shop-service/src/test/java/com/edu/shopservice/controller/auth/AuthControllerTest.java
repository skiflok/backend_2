package com.edu.shopservice.controller.auth;

import com.edu.shopservice.controller.auth.config.TestSecurityConfig;
import com.edu.shopservice.dto.auth.RegisterDto;
import com.edu.shopservice.service.AuthService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(AuthController.class)
@Import(TestSecurityConfig.class)
class AuthControllerTest {
    public static final String REGISTER_URL = "/api/v1/authorization/register";
    public static final String VALID_REGISTER_REQUEST = """
            {
              "email": "user3@example.com",
              "firstName": "John",
              "lastName": "Doe",
              "phoneNumber": "+1234567890",
              "password": "SecurePass123!"
            }
            """;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private AuthService service;

    @Test
    void registerShouldReturnToken() throws Exception {
        String tokenInHeader = "Bearer token";

        Mockito.when(service.register(any(RegisterDto.class))).thenReturn("token");

        mockMvc.perform(post(REGISTER_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(VALID_REGISTER_REQUEST))
                .andExpect(status().isCreated())
                .andExpect(header().string(AUTHORIZATION, tokenInHeader))
                .andDo(print());
    }

    @Test
    void testShouldReturnErrorMessageWhenServiceThrowEntityNotFoundException() throws Exception {
        String errorMessage = "error";
        Mockito.when(service.register(any(RegisterDto.class))).thenThrow(new EntityNotFoundException(errorMessage));

        mockMvc.perform(post(REGISTER_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(VALID_REGISTER_REQUEST))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.detail").value(errorMessage))
                .andDo(print());
    }

    //todo another test

}