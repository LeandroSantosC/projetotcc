package br.com.matraca.projetotcc.model.entity;

import java.util.List;

import org.hibernate.annotations.ManyToAny;
import org.hibernate.annotations.NotFound;

import br.com.matraca.projetotcc.model.dto.ButtonRequestDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="button")
@Getter //avisando pro lombok criar em tempo de execução todas os getters dos meus atributos
@NoArgsConstructor // avisando pro lombok criar um construtor vazio
@AllArgsConstructor // avisando pro lombok criar um construtor com todos os atributos
@EqualsAndHashCode(of = "id") //indicar que o id é a representaçao unica da entidade
public class Button {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //o generationtype pode ser UID, que é a criacao de ID aleatorio para gerar mais segurança no DB
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String image;

    @NotEmpty
    private String sound;

    @ManyToOne
    private Category category;

    @ManyToAny
    private List<User> user;

    @ManyToMany
    private List<Board> board;

    public Button(ButtonRequestDTO data){ //construtor para inicializar um objeto pelo Request, possibilitando a conversão
        this.name = data.name();
    }
}
