package br.com.cinescore.dto;

public record LoginResponseDto(String token, String role, Long id) {
}
