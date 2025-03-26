package com.edu.shopservice.controller.auth;


import com.edu.shopservice.dto.ClientDto;
import com.edu.shopservice.dto.auth.AuthDto;
import com.edu.shopservice.dto.auth.RegisterDto;
import com.edu.shopservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(
            value = "/register",
            consumes = "application/json"
    )
    @Operation(
            summary = "Регистрация пользователя",
            description = "Регистрация пользователя"
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterDto registerDto) {
        String token = authService.register(registerDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .build();
    }

    @PostMapping(
            value = "/auth",
            consumes = "application/json"
    )
    @Operation(
            summary = "Авторизация пользователя",
            description = "Принимаем логин пароль, отдаем токен"
    )
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> auth(@Valid @RequestBody AuthDto authDto) {
        String token = authService.auth(authDto);

        //todo заглушка
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .build();
    }

    @GetMapping(
            value = "/reset"
    )
    @Operation(
            summary = "Восстановление пароля",
            description = "Отправляет новый пароль на почту"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> reset(
//            @Valid @Email
            @RequestPart String email
    ) {

        authService.reset(email);
        //todo заглушка
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
