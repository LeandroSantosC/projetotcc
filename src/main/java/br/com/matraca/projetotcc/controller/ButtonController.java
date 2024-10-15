package br.com.matraca.projetotcc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.matraca.projetotcc.model.dto.ButtonRequestDTO;
import br.com.matraca.projetotcc.model.dto.ButtonResponseDTO;
import br.com.matraca.projetotcc.model.entity.Button;
import br.com.matraca.projetotcc.repository.ButtonRepository;
import br.com.matraca.projetotcc.service.ButtonService;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("button")
public class ButtonController {

    @Autowired //essa anotação serve para indicar para o spring que quando ele for instaciar o controller ele deve injetar essa dependencia 
    private ButtonService service;
    
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public ModelAndView getAll(){
        Iterable<Button> buttons = service.getAll();
        return new ModelAndView("index").addObject("buttons", buttons);
    }

    @GetMapping("cadastrar")
    public void cadastrarButton() {
        service.saveButton(new Button(null, "Comer", "imagem", "som", null, null, null));
        service.saveButton(new Button(null, "Dormir", "imagem", "som", null, null, null));
        service.saveButton(new Button(null, "Cagar", "imagem", "som", null, null, null));
        service.saveButton(new Button(null, "Trabalhar", "imagem", "som", null, null, null));
    }
    
    

}
