package br.com.matraca.projetotcc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
//aqui é onde eu faço o acesso ao meu BD, porem eu extendo o Jpa que ja tem esses métodos
//e aponto meu objeto button para criar os métodos de acordo com a estruturação do objeto
import org.springframework.stereotype.Repository;

import br.com.matraca.projetotcc.model.entity.Category;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

    Category findByName(String name); //porque tem que ser interface? -- preciso lembrar sobre <A,A>

}
