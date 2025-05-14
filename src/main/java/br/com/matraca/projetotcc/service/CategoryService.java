package br.com.matraca.projetotcc.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.matraca.projetotcc.model.entity.Category;
import br.com.matraca.projetotcc.repository.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository repository;

    public Category save(String category){
        return repository.findByName(category)
        .orElseGet(() -> {
            Category newCategory = new Category();
            newCategory.setName(category);
            newCategory.setButtons(new ArrayList<>());
            return repository.save(newCategory);
        });
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

    public Optional<Category> getById(UUID id){
        return this.repository.findById(id);
    }

    public void delete(Category category){
        repository.delete(category);
    }
}
