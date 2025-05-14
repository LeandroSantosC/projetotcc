package br.com.matraca.projetotcc.model.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="category")
@Getter //avisando pro lombok criar em tempo de execução todas os getters dos meus atributos
@Setter
@NoArgsConstructor // avisando pro lombok criar um construtor vazio
@AllArgsConstructor // avisando pro lombok criar um construtor com todos os atributos
@EqualsAndHashCode(of = "id") //indicar que o id é a representaçao unica da entidade
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE) //o generationtype pode ser UID, que é a criacao de ID aleatorio para gerar mais segurança no DB
    private UUID id;

    @NotEmpty
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "category", cascade=CascadeType.ALL, orphanRemoval = false)
    private List<Button> buttons;
    
    // public Category(ButtonRequestDTO data){ //construtor para inicializar um objeto pelo Request, possibilitando a conversão
    //     this.name = data.name();
    // }

    public void addButton(Button button) {
        if (this.buttons == null) {
            this.buttons = new ArrayList<>();
        }

        if (!this.buttons.contains(button)) {
            this.buttons.add(button);
        }

        if (button.getCategory() != this) {
            button.setCategory(this);
        }
    }

    @PrePersist
    @PreUpdate
    private void convertNameToLowerCase() {
        this.name = this.name != null ? this.name.toLowerCase() : null;
    }

//     @Override
// public String toString() {
//     return "Category [id=" + id + 
//            ", name=" + name + 
//            ", buttons=" + 
//            buttons.stream()
//                   .map(button -> button.getName()) // Converte cada botão para seu nome
//                   .collect(Collectors.joining(", ")) + // Junta os nomes em uma string separada por vírgulas
//            "]";
// }
}
