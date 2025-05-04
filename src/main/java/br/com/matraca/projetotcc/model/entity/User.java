package br.com.matraca.projetotcc.model.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.matraca.projetotcc.model.enums.Gender;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="users") // é users pq user é uma palavra reservada do banco de dados
@Getter //avisando pro lombok criar em tempo de execução todas os getters dos meus atributos
@Setter
@NoArgsConstructor // avisando pro lombok criar um construtor vazio
@AllArgsConstructor // avisando pro lombok criar um construtor com todos os atributos
@EqualsAndHashCode(of = "id") //indicar que o id é a representaçao unica da entidade
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) //o generationtype pode ser UID, que é a criacao de ID aleatorio para gerar mais segurança no DB
    private UUID id;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Auth credentials;
    @NotEmpty
    private String fullname;
    @NotEmpty
    @Column(unique = true, nullable = false)
    @Email
    private String email;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private LocalDate birthDate;
    
    private int layoutScale = 3;
    
    private String voice;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Button> button = new ArrayList<>();
    
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Board> board = new ArrayList<>();

    public User(Auth credentials, String fullname, String email, String phoneNumber, Gender gender, LocalDate birthDate){
        this.credentials = credentials;
        this.fullname = fullname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.birthDate = birthDate;
    }

    public User(int layoutScale, String voice){
        this.voice = voice;
        this.layoutScale = layoutScale;
    }

    public User(UUID id){
        this.id = id;
    }

    public User(UUID id, Auth credentials){
        this.id = id;
        this.credentials = credentials;
    }

    public User(Auth credentials, String fullname, String email){
        this.credentials = credentials;
        this.fullname = fullname;
        this.email = email;
    }
    
    // public Category(ButtonRequestDTO data){ //construtor para inicializar um objeto pelo Request, possibilitando a conversão
    //     this.name = data.name();
    // }
}
