package br.com.matraca.projetotcc.model.dto;

import br.com.matraca.projetotcc.model.entity.Button;

public record ButtonResponseDTO(Long id, String name/*aqui fica os atributos que o cliente vai receber do servidor */) { //criei um DTO como uma boa prática para conseguir retornar os valores do BD sem ser por LIST
    
    public ButtonResponseDTO(Button button){ //criando o construtor
        this(button.getId(), button.getName());//puxando os atributos da entidade
    }

}
