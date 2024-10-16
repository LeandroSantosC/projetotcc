package br.com.matraca.projetotcc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
//aqui é onde eu faço o acesso ao meu BD, porem eu extendo o Jpa que ja tem esses métodos
//e aponto meu objeto button para criar os métodos de acordo com a estruturação do objeto
import org.springframework.stereotype.Repository;

import br.com.matraca.projetotcc.model.entity.Board;
import br.com.matraca.projetotcc.model.entity.User;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>{ //porque tem que ser interface? -- preciso lembrar sobre <A,A>

    Iterable<Board> findAllByUser(User user);

    Optional<Object> findByName(String lowerCase);

    Iterable<Board> findByNameContaining(String lowerCase);
    
}
