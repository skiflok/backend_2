package com.edu.shopservice.dto.auth;

import lombok.Builder;

@Builder
public record ResetPasswordResponseDto(boolean success, String message) {
}
