package br.com.matraca.projetotcc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.matraca.projetotcc.model.entity.Board;
import br.com.matraca.projetotcc.model.entity.Button;
import br.com.matraca.projetotcc.model.entity.User;
import br.com.matraca.projetotcc.repository.BoardRepository;

@Service
public class BoardService {

    @Autowired
    BoardRepository repository;

    @Autowired
    ButtonService button;

    public Board save(Board board){
        if(board.getId() == null && repository.findByName(board.getName().toLowerCase()) != null){
            // VERIFICAR SE VIA PRECISAR ADICIONAR ERRO NO RETORNO
            return null;
        }
        return this.repository.save(board);
    }

    public Iterable<Board> getAllByUser(User user){
        return repository.findAllByUser(user);
    }

    public Iterable<Board> getAll(){
        return this.repository.findAll();
    }

    public Iterable<Board> getByName(String name){
        return this.repository.findByNameContaining(name.toLowerCase());
    }

    public void addButton(Board board, Iterable<Button> buttons){
        board.addButton(buttons);
        repository.save(board);
    }

    public Iterable<Button> getAllButtons(Board board){
        return button.getAllByBoard(board);
    }

    public void delete(Board board){
        repository.delete(board);
    }

}
