package br.com.matraca.projetotcc.repository;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.matraca.projetotcc.model.entity.User;
import br.com.matraca.projetotcc.model.enums.Role;


@Repository
public interface UserRepository extends JpaRepository<User, UUID>{ //porque tem que ser interface? -- preciso lembrar sobre <A,A>

    User findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<User> getByCredentials_Role(Role role);
}
