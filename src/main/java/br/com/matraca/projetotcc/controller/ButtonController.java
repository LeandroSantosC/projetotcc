package br.com.matraca.projetotcc.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import br.com.matraca.projetotcc.model.entity.Button;
import br.com.matraca.projetotcc.model.entity.Category;
import br.com.matraca.projetotcc.service.ButtonService;
import br.com.matraca.projetotcc.service.CategoryService;
import br.com.matraca.projetotcc.service.Scraping;


@Controller
@RequestMapping("button")
public class ButtonController {

    @Autowired //essa anotação serve para indicar para o spring que quando ele for instaciar o controller ele deve injetar essa dependencia 
    private ButtonService service;

    @Autowired
    private CategoryService cservice;

    @Autowired
    private Scraping scrap;
    
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public ModelAndView getPage(){
        Iterable<Button> buttons = service.getAll();
        return new ModelAndView("index").addObject("buttons", buttons);
    }

    @GetMapping("cadastrar")
    public void cadastrarButton() throws IOException {
        service.save(new Button(null, "Comer", scrap.getImage("comer"), "som", null, null, null));
        service.save(new Button(null, "Dormir", scrap.getImage("dormir"), "som", null, null, null));
        service.save(new Button(null, "Cagar", scrap.getImage("cagar"), "som", null, null, null));
        service.save(new Button(null, "Trabalhar", scrap.getImage("trabalhar"), "som", null, null, null));
    }
    
    

}