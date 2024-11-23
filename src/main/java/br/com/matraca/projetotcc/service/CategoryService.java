package br.com.matraca.projetotcc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.matraca.projetotcc.model.entity.Button;
import br.com.matraca.projetotcc.model.entity.Category;
import br.com.matraca.projetotcc.model.entity.User;
import br.com.matraca.projetotcc.repository.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository repository;

    @Autowired
    ButtonService button;

    public Category save(Category category){
        // if(category.getId() == null && repository.findByName(category.getName().toLowerCase()) != null){
        //     // VERIFICAR SE VIA PRECISAR ADICIONAR ERRO NO RETORNO
        //     return null;
        // }
        return this.repository.save(category);
    }

    public Iterable<Button> getAllButtons(Category category){
        return button.getAllByCategory(category);
    }

    public Iterable<Category> getAll(){
        return this.repository.findAll();
    }

    public Optional<Category> getByName(String name){
        return this.repository.findByName(name);
    }

    public List<Category> getByNameContaining(String name){
        return this.repository.findByNameContaining(name);
    }

    public Optional<Category> getById(Long id){
        return this.repository.findById(id);
    }

    public void delete(Category category){
        repository.delete(category);
    }
}
