package br.com.matraca.projetotcc.dto;

import java.time.LocalDate;

import br.com.matraca.projetotcc.model.enums.Gender;

public record RegisterDTO(
    String fullname,
    String email,
    String phoneNumber,
    Gender gender,
    LocalDate birthDate,
    String login,
    String password
) {
}
