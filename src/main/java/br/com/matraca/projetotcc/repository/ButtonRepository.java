package br.com.matraca.projetotcc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
//aqui é onde eu faço o acesso ao meu BD, porem eu extendo o Jpa que ja tem esses métodos
//e aponto meu objeto button para criar os métodos de acordo com a estruturação do objeto
import org.springframework.stereotype.Repository;

import br.com.matraca.projetotcc.model.entity.Board;
import br.com.matraca.projetotcc.model.entity.Button;
import br.com.matraca.projetotcc.model.entity.Category;
import br.com.matraca.projetotcc.model.entity.User;


@Repository
public interface ButtonRepository extends JpaRepository<Button, Long>{ //porque tem que ser interface? -- preciso lembrar sobre <A,A>

    Iterable<Button> findAllByCategory(Category category);
    Optional<Button> findByName(String name);
    List<Button> findByNameContaining(String name);

}
