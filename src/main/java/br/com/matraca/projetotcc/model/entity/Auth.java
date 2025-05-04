package br.com.matraca.projetotcc.model.entity;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import br.com.matraca.projetotcc.model.enums.AuthProvider;
import br.com.matraca.projetotcc.model.enums.Role;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="auth")
@Getter //avisando pro lombok criar em tempo de execução todas os getters dos meus atributos
@Setter
@NoArgsConstructor // avisando pro lombok criar um construtor vazio
@AllArgsConstructor // avisando pro lombok criar um construtor com todos os atributos
@EqualsAndHashCode(of = "id") //indicar que o id é a representaçao unica da entidade
public class Auth implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) //o generationtype pode ser UID, que é a criacao de ID aleatorio para gerar mais segurança no DB
    private UUID id;

    @Column(nullable = false)
    private boolean emailVerified = false;

    @Column
    private String verificationToken;

    @Column(unique = true, nullable = false)
    private String login;

    @JsonIgnore
    @Column(nullable = true)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Auth(String login, String password, Role role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }
    
    public Auth(UUID id, Role role){
      this.id = id;
      this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      if(null == this.role) {
          return List.of(new SimpleGrantedAuthority("ROLE_PUBLIC"));
      }
      else return switch (this.role) {
          case ADMIN -> List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
          case USER -> List.of(new SimpleGrantedAuthority("ROLE_USER"));
          default -> List.of(new SimpleGrantedAuthority("ROLE_PUBLIC"));
      };
    }

    @Override
    public boolean isAccountNonExpired() {
      // TODO Auto-generated method stub
      return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
      // TODO Auto-generated method stub
      return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
      // TODO Auto-generated method stub
      return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
      // TODO Auto-generated method stub
      return UserDetails.super.isEnabled();
    }

    @Override
    public String getUsername() {
      return this.login;
    }
    
    // public Category(ButtonRequestDTO data){ //construtor para inicializar um objeto pelo Request, possibilitando a conversão
    //     this.name = data.name();
    // }
}
