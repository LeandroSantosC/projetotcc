package br.com.matraca.projetotcc.service;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.matraca.projetotcc.model.entity.Board;
import br.com.matraca.projetotcc.model.entity.Category;
import br.com.matraca.projetotcc.repository.BoardRepository;
import br.com.matraca.projetotcc.repository.CategoryRepository;

public class BoardService {

    @Autowired
    BoardRepository repository;

    Board board;

    public Board createBoard(Board board){
        return this.repository.save(board);
    }

    public Iterable<Category> getAll(){
        return this.repository.findAll();
    }

    public Category getByName(String name){
        return this.repository.findByName(name);
    }

}
