package br.com.matraca.projetotcc.service;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.matraca.projetotcc.model.entity.Category;
import br.com.matraca.projetotcc.repository.CategoryRepository;

public class CategoryService {

    @Autowired
    CategoryRepository repository;

    public Category saveCategory(Category category){
        return this.repository.save(category);
    }

    public Iterable<Category> getAll(){
        return this.repository.findAll();
    }

    public Category getByName(String name){
        return this.repository.findByName(name);
    }

}
