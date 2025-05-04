package br.com.matraca.projetotcc.repository;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import br.com.matraca.projetotcc.model.entity.Auth;


@Repository
public interface AuthRepository extends JpaRepository<Auth, UUID>{ //porque tem que ser interface? -- preciso lembrar sobre <A,A>

    Optional<UserDetails> findByLogin(String username);
    Optional<UserDetails> findByUser_Email(String email);
    Optional<UserDetails> findByUser_PhoneNumber(String phonenumber);
    Optional<Auth> findByVerificationToken(String token);
    boolean existsByLogin(String username);
    
}
