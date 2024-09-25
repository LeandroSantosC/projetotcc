package br.com.matraca.projetotcc.figura;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Table(name="figuras")
@Entity(name="figuras")
public class Figura {
    private Long id;
    private String nome;
    private String imagem;
    private String som;
}
