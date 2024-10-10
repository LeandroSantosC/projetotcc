package br.com.matraca.projetotcc.service;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.matraca.projetotcc.model.entity.User;
import br.com.matraca.projetotcc.repository.UserRepository;

public class UserService {

    @Autowired
    UserRepository repository;

    public User saveUser(User user){
        return this.repository.save(user);
    }

    public User getByName(String name){
        return this.repository.findByName(name);
    }

    public User getByEmail(String email){
        return this.repository.findByEmail(email);
    }

}
