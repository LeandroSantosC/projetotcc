package br.com.matraca.projetotcc.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.matraca.projetotcc.model.entity.Board;
import br.com.matraca.projetotcc.model.entity.Button;
import br.com.matraca.projetotcc.model.entity.Category;
import br.com.matraca.projetotcc.model.entity.User;
import br.com.matraca.projetotcc.service.BoardService;
import br.com.matraca.projetotcc.service.ButtonService;
import br.com.matraca.projetotcc.service.CategoryService;
import br.com.matraca.projetotcc.service.Scraping;


@Controller
@RequestMapping("button")
public class ButtonController {

    @Autowired //essa anotação serve para indicar para o spring que quando ele for instaciar o controller ele deve injetar essa dependencia 
    private ButtonService service;

    @Autowired
    private BoardService boardService;

    @Autowired
    private CategoryService cservice;

    @Autowired
    private Scraping scrap;
    
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public ModelAndView getPage() throws IOException{
        Iterable<Button> buttons = service.getAll();
        for(Button button : buttons){
            if("".equals(button.getImage())){
                button.setImage(scrap.getImage(button.getName()));
            }
        }
        Iterable<Board> boards = boardService.getAll();
        Iterable<Category> categories = cservice.getAll();
        ModelAndView mv = new ModelAndView("index").addObject("buttons", buttons);
        mv.addObject("boards", boards);
        mv.addObject("categories", categories);

        return mv;
    }

    @GetMapping("cadastrar")
    public void cadastrarButton() throws IOException {
        //         List<Button> lista1 = new ArrayList<Button>();

        // // Verbos
        // lista1.add(service.save(new Button(null, "comer", scrap.getImage("comer"), "som", null, null, null)));
        // lista1.add(service.save(new Button(null, "dormir", scrap.getImage("dormir"), "som", null, null, null)));
        // lista1.add(service.save(new Button(null, "cagar", scrap.getImage("cagar"), "som", null, null, null)));
        // lista1.add(service.save(new Button(null, "trabalhar", scrap.getImage("trabalhar"), "som", null, null, null)));
        // cservice.save(new Category(null, "verbos", lista1, null));

        // List<Button> lista2 = new ArrayList<Button>();

        // // Frutas
        // lista2.add(service.save(new Button(null, "maçã", scrap.getImage("maça"), "som", null, null, null)));
        // lista2.add(service.save(new Button(null, "banana", scrap.getImage("banana"), "som", null, null, null)));
        // lista2.add(service.save(new Button(null, "laranja", scrap.getImage("laranja"), "som", null, null, null)));
        // cservice.save(new Category(null,"frutas", lista2, null));

        // List<Button> lista3 = new ArrayList<Button>();

        // // Animais
        // lista3.add(service.save(new Button(null, "cachorro", scrap.getImage("cachorro"), "som", null, null, null)));
        // lista3.add(service.save(new Button(null, "gato", scrap.getImage("gato"), "som", null, null, null)));
        // lista3.add(service.save(new Button(null, "elefante", scrap.getImage("elefante"), "som", null, null, null)));
        // cservice.save(new Category(null,"animais", lista3, null));

        // List<Button> lista4 = new ArrayList<Button>();

        // // Sentimentos
        // lista4.add(service.save(new Button(null, "feliz", scrap.getImage("feliz"), "som", null, null, null)));
        // lista4.add(service.save(new Button(null, "triste", scrap.getImage("triste"), "som", null, null, null)));
        // lista4.add(service.save(new Button(null, "bravo", scrap.getImage("bravo"), "som", null, null, null)));
        // cservice.save(new Category(null,"sentimentos", lista4, null));

        // List<Button> lista5 = new ArrayList<Button>();

        // // Cores
        // lista5.add(service.save(new Button(null, "vermelho", scrap.getImage("vermelho"), "som", null, null, null)));
        // lista5.add(service.save(new Button(null, "azul", scrap.getImage("azul"), "som", null, null, null)));
        // lista5.add(service.save(new Button(null, "verde", scrap.getImage("verde"), "som", null, null, null)));
        // lista5.add(service.save(new Button(null, "amarelo", scrap.getImage("amarelo"), "som", null, null, null)));
        // cservice.save(new Category(null,"cores", lista5, null));

        // List<Button> lista6 = new ArrayList<Button>();

        // // Locais
        // lista6.add(service.save(new Button(null, "casa", scrap.getImage("casa"), "som", null, null, null)));
        // lista6.add(service.save(new Button(null, "escola", scrap.getImage("escola"), "som", null, null, null)));
        // lista6.add(service.save(new Button(null, "parque", scrap.getImage("parque"), "som", null, null, null)));
        // lista6.add(service.save(new Button(null, "supermercado", scrap.getImage("supermercado"), "som", null, null, null)));
        // cservice.save(new Category(null,"locais", lista6, null));

        // List<Button> lista7 = new ArrayList<Button>();

        // // Profissões
        // lista7.add(service.save(new Button(null, "médico", scrap.getImage("medico"), "som", null, null, null)));
        // lista7.add(service.save(new Button(null, "professor", scrap.getImage("professor"), "som", null, null, null)));
        // lista7.add(service.save(new Button(null, "bombeiro", scrap.getImage("bombeiro"), "som", null, null, null)));
        // lista7.add(service.save(new Button(null, "engenheiro", scrap.getImage("engenheiro"), "som", null, null, null)));
        // cservice.save(new Category(null,"profissões", lista7, null));

        // List<Button> lista8 = new ArrayList<Button>();

        // // Pronomes
        // lista8.add(service.save(new Button(null, "eu", scrap.getImage("eu"), "som", null, null, null)));
        // lista8.add(service.save(new Button(null, "você", scrap.getImage("voce"), "som", null, null, null)));
        // lista8.add(service.save(new Button(null, "ele", scrap.getImage("ele"), "som", null, null, null)));
        // lista8.add(service.save(new Button(null, "nós", scrap.getImage("nos"), "som", null, null, null)));
        // cservice.save(new Category(null,"pronomes", lista8, null));

        // List<Button> lista9 = new ArrayList<Button>();

        // // Interjeições
        // lista9.add(service.save(new Button(null, "uau", scrap.getImage("uau"), "som", null, null, null)));
        // lista9.add(service.save(new Button(null, "ei", scrap.getImage("ei"), "som", null, null, null)));
        // lista9.add(service.save(new Button(null, "ah!", scrap.getImage("ah"), "som", null, null, null)));
        // lista9.add(service.save(new Button(null, "puxa", scrap.getImage("puxa"), "som", null, null, null)));
        // cservice.save(new Category(null,"interjeições", lista9, null));

        // List<Button> lista10 = new ArrayList<Button>();

        // // Números
        // lista10.add(service.save(new Button(null, "um", scrap.getImage("um"), "som", null, null, null)));
        // lista10.add(service.save(new Button(null, "dois", scrap.getImage("dois"), "som", null, null, null)));
        // lista10.add(service.save(new Button(null, "três", scrap.getImage("tres"), "som", null, null, null)));
        // lista10.add(service.save(new Button(null, "quatro", scrap.getImage("quatro"), "som", null, null, null)));
        // cservice.save(new Category(null,"números", lista10, null));

    }
    

}