package br.com.matraca.projetotcc.model.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="board")
@Getter //avisando pro lombok criar em tempo de execução todas os getters dos meus atributos
@NoArgsConstructor // avisando pro lombok criar um construtor vazio
@AllArgsConstructor // avisando pro lombok criar um construtor com todos os atributos
@EqualsAndHashCode(of = "id") //indicar que o id é a representaçao unica da entidade
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE) //o generationtype pode ser UID, que é a criacao de ID aleatorio para gerar mais segurança no DB
    private Long id;

    @NotEmpty
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    private List<Button> button;
    
    // public Category(ButtonRequestDTO data){ //construtor para inicializar um objeto pelo Request, possibilitando a conversão
    //     this.name = data.name();
    // }

    @PrePersist
    @PreUpdate
    private void convertNameToLowerCase() {
        this.name = this.name != null ? this.name.toLowerCase() : null;
    }

    public void addButton(Iterable<Button> buttons) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addButton'");
    }
}