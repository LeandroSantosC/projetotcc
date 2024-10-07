package br.com.matraca.projetotcc.model.button;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name="buttons")
@Entity(name="buttons")
@Getter //avisando pro lombok criar em tempo de execução todas os getters dos meus atributos
@NoArgsConstructor // avisando pro lombok criar um construtor vazio
@AllArgsConstructor // avisando pro lombok criar um construtor com todos os atributos
@EqualsAndHashCode(of = "id") //indicar que o id é a representaçao unica da entidade
public class Button {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) //o generationtype pode ser UID, que é a criacao de ID aleatorio para gerar mais segurança no DB
    private Long id;
    private String name;
    // private Image image;
    // private Sound sound;
    // private Category category;

    public Button(ButtonRequestDTO data){ //construtor para inicializar um objeto pelo Request, possibilitando a conversão
        this.name = data.name();
    }
}
