package br.com.matraca.projetotcc.model.button;

import org.springframework.data.jpa.repository.JpaRepository;
//aqui é onde eu faço o acesso ao meu BD, porem eu extendo o Jpa que ja tem esses métodos
//e aponto meu objeto button para criar os métodos de acordo com a estruturação do objeto

public interface ButtonRepository extends JpaRepository<Button, Long>{ //porque tem que ser interface? -- preciso lembrar sobre <A,A>

}
