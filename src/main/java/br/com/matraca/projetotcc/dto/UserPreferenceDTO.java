package br.com.matraca.projetotcc.dto;

import java.util.List;

import br.com.matraca.projetotcc.model.entity.LayoutScale;

public record UserPreferenceDTO(
    String voice,
    LayoutScale layoutScale,
    List<CardLayoutDTO> cards,
    List<CardLayoutDTO> boards
) {
}
