package br.com.matraca.projetotcc.service;

import java.util.Optional;
import java.util.UUID;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.matraca.projetotcc.dto.RegisterDTO;
import br.com.matraca.projetotcc.model.entity.Auth;
import br.com.matraca.projetotcc.model.entity.Board;
import br.com.matraca.projetotcc.model.entity.Button;
import br.com.matraca.projetotcc.model.enums.Role;
import br.com.matraca.projetotcc.model.entity.User;
import br.com.matraca.projetotcc.repository.AuthRepository;
import br.com.matraca.projetotcc.repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    UserRepository repository;
    @Autowired
    AuthRepository authRepository;
    @Autowired
    BoardService boardService;
    @Autowired
    ButtonService buttonService;
    @Autowired
    private EmailService emailService;

    public static int orderCont = 1;

    @Transactional
    public void registerUser(User user, String login, String password, Role role) {
        if (authRepository.existsByLogin(login)) {
            throw new RuntimeException("Login already exists");
        }

        if (repository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("User already exists");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(password);

        Auth auth = new Auth();
        String token = UUID.randomUUID().toString();
        auth.setVerificationToken(token);
        auth.setEmailVerified(false);
        auth.setLogin(login);
        auth.setPassword(encryptedPassword);
        auth.setUser(user);
        auth.setRole(role);
        user.setCredentials(auth);

        try {
            if (role == Role.USER) {
                emailService.sendVerificationEmail(login, token);
            }
            repository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao cadastrar usuario: " + e.getMessage(), e);
        }
    }

    @Transactional
    public User registerUser(User user) {
        if (repository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("User already exists");
        }

        Auth auth = user.getCredentials();

        String encryptedPassword = new BCryptPasswordEncoder().encode(auth.getPassword());
        if (auth.getLogin() == null) {
            auth.setLogin(user.getEmail());
        }
        if (auth.getRole() == null) {
            auth.setRole(Role.USER);
        }
        auth.setPassword(encryptedPassword);

        return repository.save(user);
    }

    @Transactional
    public User updateRoleUser(User user, Role role) {
        if (role == null) {
            throw new RuntimeException("Nenhum campo para atualizar foi fornecido.");
        }

        if (!authRepository.existsByLogin(user.getCredentials().getLogin())) {
            throw new RuntimeException("User don't exists");
        }

        user.getCredentials().setRole(role);
        try {
            return repository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar usuario: " + e.getMessage(), e);
        }
    }

    @Transactional
    public User updateUser(User user, User updates) {
        if (updates.getFullname() == null
                && updates.getEmail() == null
                && updates.getBirthDate() == null
                && updates.getGender() == null
                && updates.getPhoneNumber() == null
                && updates.getCredentials() == null) {
            throw new RuntimeException("Nenhum campo para atualizar foi fornecido.");
        }

        if (!authRepository.existsByLogin(user.getCredentials().getLogin())) {
            throw new RuntimeException("User don't exists");
        }

        Optional.ofNullable(updates.getFullname()).ifPresent(user::setFullname);
        Optional.ofNullable(updates.getEmail()).ifPresent(user::setEmail);
        Optional.ofNullable(updates.getBirthDate()).ifPresent(user::setBirthDate);
        Optional.ofNullable(updates.getGender()).ifPresent(user::setGender);
        Optional.ofNullable(updates.getPhoneNumber()).ifPresent(user::setPhoneNumber);
        Optional.ofNullable(updates.getCredentials()).ifPresent(credentials -> {
            if (credentials.getLogin() != null) {
                user.getCredentials().setLogin(credentials.getLogin());
            }
            if (credentials.getPassword() != null) {
                String encryptedPassword = new BCryptPasswordEncoder().encode(credentials.getPassword());
                user.getCredentials().setPassword(encryptedPassword);
            }
            if (credentials.getRole() != null) {
                user.getCredentials().setRole(credentials.getRole());
            }
        });

        try {
            return repository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar usuario: " + e.getMessage(), e);
        }
    }

    @Transactional
    public void deleteUser(User user) {
        if (!authRepository.existsByLogin(user.getCredentials().getLogin())) {
            throw new RuntimeException("User don't exists");
        }

        try {
            repository.delete(user);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar usuário: " + e.getMessage(), e);
        }
    }

    @Transactional
    public Button createPublicCard(Button newButton) throws RuntimeException {
        User user = repository.getByCredentials_Role(Role.PUBLIC)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Public user not found"));

        try {
            Button button = buttonService.create(user, newButton);
            repository.save(user);

            return button;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar: " + e.getMessage(), e);
        }
    }

    @Transactional
    public Button updatePublicCard(Button updates) throws RuntimeException {
        User user = repository.getByCredentials_Role(Role.PUBLIC)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Public user not found"));

        if (updates.getId() == null) {
            throw new RuntimeException("ID do botão não pode ser nulo.");
        }
        if (updates.getName() == null && updates.getImage() == null && updates.getSound() == null && updates.getCategory() == null) {
            throw new RuntimeException("Nenhum campo para atualizar foi fornecido.");
        }

        try {
            Button button = buttonService.update(user, updates);
            repository.save(user);
            return button;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar: " + e.getMessage(), e);
        }
    }

    @Transactional
    public void deletePublicCard(Button button) throws RuntimeException {
        if (button.getId() == null) {
            throw new RuntimeException("ID do botão não pode ser nulo.");
        }

        User user = repository.getByCredentials_Role(Role.PUBLIC)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Public user not found"));

        try {
            buttonService.delete(user, button);
            repository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar: " + e.getMessage(), e);
        }
    }

    @Transactional
    public Button createCard(User user, Button newButton) throws RuntimeException {
        if (user == null) {
            throw new RuntimeException("ID do usuário não pode ser nulo.");
        }

        try {
            Button button = buttonService.create(user, newButton);
            repository.save(user);
            return button;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar: " + e.getMessage(), e);
        }
    }

    @Transactional
    public Button updateCard(User user, Button updates) throws RuntimeException {
        if (user == null) {
            throw new RuntimeException("ID do usuário não pode ser nulo.");
        }
        if (updates.getId() == null) {
            throw new RuntimeException("ID do botão não pode ser nulo.");
        }
        if (updates.getName() == null && updates.getImage() == null && updates.getSound() == null && updates.getCategory() == null) {
            throw new RuntimeException("Nenhum campo para atualizar foi fornecido.");
        }

        try {
            Button button = buttonService.update(user, updates);
            repository.save(user);
            return button;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar: " + e.getMessage(), e);
        }
    }

    @Transactional
    public void deleteCard(User user, Button button) throws RuntimeException {
        if (button.getId() == null) {
            throw new RuntimeException("ID do botão não pode ser nulo.");
        }

        try {
            buttonService.delete(user, button);
            repository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar: " + e.getMessage(), e);
        }
    }

    @Transactional
    public Board createBoard(User user, Board newBoard) throws RuntimeException {
        if (user == null) {
            throw new RuntimeException("ID do usuário não pode ser nulo.");
        }

        try {
            Board board = boardService.create(user, newBoard);
            repository.save(user);
            return board;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar: " + e.getMessage(), e);
        }
    }

    @Transactional
    public Board updateBoard(User user, Board updates) throws RuntimeException {
        if (user == null) {
            throw new RuntimeException("ID do usuário não pode ser nulo.");
        }
        if (updates.getId() == null) {
            throw new RuntimeException("ID da prancha não pode ser nulo.");
        }
        if (updates.getName() == null && updates.getButton() == null) {
            throw new RuntimeException("Nenhum campo para atualizar foi fornecido.");
        }

        try {
            Board board = boardService.update(user, updates);
            repository.save(user);
            return board;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar: " + e.getMessage(), e);
        }
    }

    @Transactional
    public void deleteBoard(User user, Board button) throws RuntimeException {
        if (button.getId() == null) {
            throw new RuntimeException("ID da prancha não pode ser nulo.");
        }

        try {
            boardService.delete(user, button);
            repository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar: " + e.getMessage(), e);
        }
    }
}
