package br.com.matraca.projetotcc.model.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
@AllArgsConstructor
public class LayoutScale {
    private float card;
    private int board;
}

