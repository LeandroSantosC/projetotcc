package br.com.matraca.projetotcc.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
//aqui é onde eu faço o acesso ao meu BD, porem eu extendo o Jpa que ja tem esses métodos
//e aponto meu objeto button para criar os métodos de acordo com a estruturação do objeto
import org.springframework.stereotype.Repository;

import br.com.matraca.projetotcc.model.entity.Category;
import br.com.matraca.projetotcc.model.entity.User;


@Repository
public interface CategoryRepository extends CrudRepository<Category, UUID>{

    Optional<Category> findByName(String name); //porque tem que ser interface? -- preciso lembrar sobre <A,A>
    List<Category> findByNameContaining(String name);
    boolean existsByName(String name);

}
