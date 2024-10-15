package br.com.matraca.projetotcc.repository;

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

    Iterable<Button> findByUser(User user);
    Iterable<Button> findByBoard(Board board);
    Iterable<Button> findByCategory(Category category);

}
