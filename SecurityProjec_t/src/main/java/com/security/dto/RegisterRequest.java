package com.security.dto;

public record RegisterRequest(String userName, String password,Role role) {
}
