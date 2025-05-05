package br.com.matraca.projetotcc.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.matraca.projetotcc.dto.ApiResponse;
import br.com.matraca.projetotcc.model.entity.Auth;
import br.com.matraca.projetotcc.model.entity.Button;
import br.com.matraca.projetotcc.model.entity.User;
import br.com.matraca.projetotcc.service.Scraping;
import br.com.matraca.projetotcc.service.UserService;

@RestController
@RequestMapping("/api/card")
public class CardController {

    @Autowired
    private UserService userService;

    @Autowired
    private Scraping scrap;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Button>>> getCards(@AuthenticationPrincipal Auth auth) {
        User user = auth.getUser();
        List<Button> buttons = user.getButton();
        return ResponseEntity.ok(ApiResponse.success(buttons));
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Button>> updateCard(@AuthenticationPrincipal Auth auth, @PathVariable UUID id, @RequestBody Button updates) {
        User user = auth.getUser();
        updates.setId(id);
        Button card = userService.updateCard(user, updates);

        return ResponseEntity.ok(ApiResponse.success(card));
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<ApiResponse<Button>> createCard(@AuthenticationPrincipal Auth auth, @RequestBody Button newCard) {
        Button button = userService.createCard(auth.getUser(), newCard);

        return ResponseEntity.ok(ApiResponse.success(button));
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCard(@AuthenticationPrincipal Auth auth, @PathVariable UUID id) {
        User user = auth.getUser();
        Button button = new Button(id);
        userService.deleteCard(user, button);

        return ResponseEntity.ok(ApiResponse.success("Card " + button.getName() + " deletado com sucesso!"));
    }

    @PostMapping(value = "scrap", consumes = "text/plain", produces = "text/plain")
        public ResponseEntity<ApiResponse<String>> scrap(@RequestBody String buttonName) throws IOException {
        String url = scrap.getImage(buttonName);
        
        return ResponseEntity.ok(ApiResponse.success(url));
    }

}
