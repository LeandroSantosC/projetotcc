package br.com.matraca.projetotcc.dto;

import java.util.UUID;

public record CardLayoutDTO(
    UUID id,
    int position,
    boolean visible
) {
}
