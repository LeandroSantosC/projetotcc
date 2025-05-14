package br.com.matraca.projetotcc.model.entity;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="button", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "user_id"})})
@Getter //avisando pro lombok criar em tempo de execução todas os getters dos meus atributos
@Setter
@NoArgsConstructor // avisando pro lombok criar um construtor vazio
@AllArgsConstructor // avisando pro lombok criar um construtor com todos os atributos
@EqualsAndHashCode(of = "id") //indicar que o id é a representaçao unica da entidade
public class Button {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID) //o generationtype pode ser UID, que é a criacao de ID aleatorio para gerar mais segurança no DB
    private UUID id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String image;

    @NotEmpty
    private String sound = "";

    @ManyToOne(fetch = FetchType.EAGER) // CASCADE MUITO IMPORTANTE PARA PERSISTENCIA
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private int position;

    private boolean isVisible = true;

    public Button(String name, String img, String sound, Category category){
        this.name = name;
        this.image = img;
        this.sound = sound;
        this.category = category;
    }

    public Button(UUID id){
        this.id = id;
    }

    @PrePersist
    @PreUpdate
    private void convertNameToLowerCase() {
        this.name = this.name != null ? this.name.toLowerCase() : null;
    }


    // @Override
    // public String toString() {
    //     return "Button [id=" + id + ", name=" + name + ", image=" + image + ", sound=" + sound + ", category="
    //             + category + ", position=" + position + ", isVisible=" + isVisible + "]";
    // }

}
