package br.com.matraca.projetotcc.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}