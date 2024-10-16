package br.com.matraca.projetotcc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.matraca.projetotcc.model.entity.Board;
import br.com.matraca.projetotcc.model.entity.Button;
import br.com.matraca.projetotcc.model.entity.Category;
import br.com.matraca.projetotcc.model.entity.User;
import br.com.matraca.projetotcc.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository repository;
    @Autowired
    ButtonService button;
    @Autowired
    BoardService board;

    public User save(User user){
        return this.repository.save(user);
    }

    public User getByName(String name){
        return this.repository.findByName(name);
    }

    public User getByEmail(String email){
        return this.repository.findByEmail(email);
    }

    public Iterable<Button> getAllButtons(User user){
        return button.getAllByUser(user);
    }

    public Iterable<Board> getAllBoards(User user){
        return board.getAllByUser(user);
    }
    
    public void delete(User user){
        repository.delete(user);
    }
}
