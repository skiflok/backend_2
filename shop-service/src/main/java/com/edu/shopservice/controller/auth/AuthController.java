package com.edu.shopservice.controller.auth;


import com.edu.shopservice.dto.auth.AuthDto;
import com.edu.shopservice.dto.auth.RegisterDto;
import com.edu.shopservice.dto.auth.ResetPasswordResponseDto;
import com.edu.shopservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;

@Slf4j
@Tag(
        name = "AuthController",
        description = "Контроллер для авторизации"
)
@RestController()
@RequiredArgsConstructor
@RequestMapping(
        value = "/api/v1/authorization",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class AuthController {
    private final AuthService authService;

    @PostMapping(value = "/register", consumes = "application/json")
    @Operation(
            summary = "Регистрация пользователя",
            description = "Регистрация пользователя"
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterDto registerDto) throws AuthenticationException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authService.register(registerDto))
                .build();
    }

    @PostMapping(value = "/auth", consumes = "application/json")
    @Operation(
            summary = "Авторизация пользователя",
            description = "Принимаем логин пароль, отдаем токен"
    )
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> auth(@Valid @RequestBody AuthDto authDto) throws AuthenticationException {
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authService.auth(authDto))
                .build();
    }

    @PostMapping(value = "/reset")
    @Operation(
            summary = "Восстановление пароля",
            description = "Отправляет новый пароль на почту"
    )
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResetPasswordResponseDto reset(@RequestPart String email) throws AuthenticationException {
        return authService.reset(email);
    }
}
