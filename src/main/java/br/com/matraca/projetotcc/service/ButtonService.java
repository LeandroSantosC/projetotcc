package br.com.matraca.projetotcc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.matraca.projetotcc.model.entity.Board;
import br.com.matraca.projetotcc.model.entity.Button;
import br.com.matraca.projetotcc.model.entity.Category;
import br.com.matraca.projetotcc.repository.ButtonRepository;

@Service
public class ButtonService {

    @Autowired
    ButtonRepository repository;

    public Iterable<Button> getAll(){
        Iterable<Button> buttonList = repository.findAll();
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
