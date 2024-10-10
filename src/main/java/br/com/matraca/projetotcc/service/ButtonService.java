package br.com.matraca.projetotcc.service;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.matraca.projetotcc.model.dto.ButtonResponseDTO;
import br.com.matraca.projetotcc.model.entity.Board;
import br.com.matraca.projetotcc.model.entity.Button;
import br.com.matraca.projetotcc.model.entity.Category;
import br.com.matraca.projetotcc.repository.ButtonRepository;
import java.util.List;

public class ButtonService {

    @Autowired
    ButtonRepository repository;

    public List<ButtonResponseDTO> getAll(){ //mudei de List de Button para ButtonResponseDTO como uma boa prática

        List<ButtonResponseDTO> buttonList = repository.findAll().stream().map(ButtonResponseDTO::new).toList(); //troquei para pegar os dados da lista, criar um funil e ainda mapear com o DTO
        return buttonList;
    }

    public Button saveButton(Button button){
        return this.repository.save(button);
    }

    public Iterable<Button> getByBoard(Board board){
        return this.repository.findByBoard(board);
    }

    public Iterable<Button> getByCategory(Category category){
        return this.repository.findByCategory(category);
    }


}
