package br.com.matraca.projetotcc.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.matraca.projetotcc.model.entity.Board;
import br.com.matraca.projetotcc.model.entity.Button;
import br.com.matraca.projetotcc.model.entity.Category;
import br.com.matraca.projetotcc.model.entity.User;
import br.com.matraca.projetotcc.repository.ButtonRepository;

@Service
public class ButtonService {

    @Autowired
    ButtonRepository repository;

    public Iterable<Button> getAll(){
        Iterable<Button> buttonList = repository.findAll();
        return buttonList;
    }

    public Button save(Button button){
        if(button.getId() == null && repository.findByName(button.getName().toLowerCase()) != null){
            // VERIFICAR SE VIA PRECISAR ADICIONAR ERRO NO RETORNO
            return null;
        }
        return this.repository.save(button);
    }

    public Iterable<Button> getAllByBoard(Board board){
        return this.repository.findAllByBoard(board);
    }

    public Iterable<Button> getAllByCategory(Category category){
        return this.repository.findAllByCategory(category);
    }

    public Iterable<Button> getAllByUser(User user){
        return this.repository.findAllByUser(user);
    }

    public Optional<Button> getByName(String name){
        return this.repository.findByName(name);
    }

    public Optional<Button> getById(Long id){
        return this.repository.findById(id);
    }

    public void delete(Button button){
        repository.delete(button);
    }

}
