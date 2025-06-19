package br.com.matraca.projetotcc.dto;

public record LoginDTO(
    String login,
    String password,
    boolean rememberMe
) {
}
