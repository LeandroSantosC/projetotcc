package br.com.matraca.projetotcc.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.matraca.projetotcc.model.entity.Category;
import br.com.matraca.projetotcc.model.entity.Button;
import br.com.matraca.projetotcc.service.ButtonService;


@RestController
@RequestMapping("/api/card")
public class CardController {

    @Autowired //essa anotação serve para indicar para o spring que quando ele for instaciar o controller ele deve injetar essa dependencia 
    private ButtonService service;
    
    @GetMapping
    public Iterable<Button> getCards() throws IOException{
        Iterable<Button> buttons = service.getAll();

        for(Button button : buttons){
            System.out.println(button);
        }
        
        return buttons;
    }

    @PatchMapping("{id}")
    public ResponseEntity<String> updateCard(@PathVariable Long id, @RequestBody Button updates) {
        service.update(id, updates);
        return ResponseEntity.ok("Recurso atualizado parcialmente com sucesso!");
    }

    @PostMapping("/")
    public ResponseEntity<String> createCard(@RequestBody Button newCard) throws IOException {

        service.create(newCard);
        return ResponseEntity.ok("Recurso atualizado parcialmente com sucesso!");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCard(@PathVariable Long id) throws IOException {


        service.delete(id);
        return ResponseEntity.ok("Recurso atualizado parcialmente com sucesso!");
    }
}