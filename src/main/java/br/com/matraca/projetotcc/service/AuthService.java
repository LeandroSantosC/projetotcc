package br.com.matraca.projetotcc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import br.com.matraca.projetotcc.repository.AuthRepository;

@Service
public class AuthService implements UserDetailsService{

    @Autowired
    private AuthRepository repository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return repository.findByUser_Email(login).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
   
}
