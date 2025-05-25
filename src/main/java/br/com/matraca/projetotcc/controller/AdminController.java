package br.com.matraca.projetotcc.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.matraca.projetotcc.dto.ApiResponse;
import br.com.matraca.projetotcc.model.entity.Button;
import br.com.matraca.projetotcc.model.entity.User;
import br.com.matraca.projetotcc.model.enums.Role;
import br.com.matraca.projetotcc.repository.UserRepository;
import br.com.matraca.projetotcc.service.UserService;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository repository;

    @GetMapping("/card")
    public ResponseEntity<ApiResponse<List<Button>>> getPublicCards() {
        User user = repository.getByCredentials_Role(Role.PUBLIC)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Public user not found"));
        
        List<Button> buttons = user.getButton();

        return ResponseEntity.ok(ApiResponse.success(buttons));
    }

    @PatchMapping("/card/list")
    public ResponseEntity<ApiResponse<List<Button>>> patchResource(@RequestBody List<Button> buttonList) throws IOException {

        String erros = "";
        List<Button> buttonsAdded = new ArrayList<>();

        for (Button newButton : buttonList) {
            try {
                Button buttonWithId = userService.createPublicCard(newButton);
                buttonsAdded.add(buttonWithId);
            } catch (Exception e) {
                erros += "Erro ao criar o botão: " + newButton.getName() + " - " + e.getMessage() + "\n";
            }
        }

        if (erros != "") {
            return ResponseEntity.badRequest().body(ApiResponse.error(erros));
        }

        return ResponseEntity.ok(ApiResponse.success(buttonsAdded));

    }

    @PatchMapping("/card/{id}")
    public ResponseEntity<ApiResponse<Button>> updatePublicCard(@PathVariable UUID id, @RequestBody Button updates) {
        updates.setId(id);
        Button button = userService.updatePublicCard(updates);

        return ResponseEntity.ok(ApiResponse.success(button));
    }

    @PostMapping("/card")
    public ResponseEntity<ApiResponse<Button>> createPublicCard(@RequestBody Button newCard) {
            Button button = userService.createPublicCard(newCard);


        return ResponseEntity.ok(ApiResponse.success(button));
    }

    @DeleteMapping("/card/{id}")
    public ResponseEntity<ApiResponse<String>> deletePublicCard(@PathVariable UUID id) {
        Button button = new Button(id);
        userService.deletePublicCard(button);

        return ResponseEntity.ok(ApiResponse.success("Card " + button.getName() + " deletado com sucesso!"));
    }

    @GetMapping("/user")
    public ResponseEntity<ApiResponse<List<User>>> getUsers() {
            List<User> users = repository.findAll();
            return ResponseEntity.ok(ApiResponse.success(users));
    }

    @PatchMapping("/user/{id}")
    public ResponseEntity<ApiResponse<User>> updateRoleUser(@PathVariable UUID id, Role role) {
        User user = repository.findById(id).orElseThrow(() -> new RuntimeException("Usuário de ID:" + id + " não encontrado!"));
        User updatedUser = userService.updateRoleUser(user, role);

        return ResponseEntity.ok(ApiResponse.success(updatedUser));
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable UUID id) {
            User user = repository.findById(id).orElseThrow(() -> new RuntimeException("Usuário de ID:" + id + " não encontrado!"));
            userService.deleteUser(user);

        return ResponseEntity.ok(ApiResponse.success("Usuário: " + user.getEmail() + " deletado com sucesso!"));
    }
}
