package br.com.matraca.projetotcc.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.matraca.projetotcc.model.entity.Button;
import br.com.matraca.projetotcc.model.entity.User;


@Repository
public interface ButtonRepository extends JpaRepository<Button, UUID>{ //porque tem que ser interface? -- preciso lembrar sobre <A,A>

    Iterable<Button> findAllByCategory(String category);
    Optional<Button> findByName(String name);
    Optional<Button> findByIdAndUser(UUID id, User user);
    List<Button> findByNameContaining(String name);
    boolean existsByName(String name);
    List<Button> findAllByUser(User user);

}
