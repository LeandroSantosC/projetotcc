package br.com.matraca.projetotcc.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import br.com.matraca.projetotcc.dto.ButtonDTO;
import br.com.matraca.projetotcc.model.entity.Board;
import br.com.matraca.projetotcc.model.entity.Button;
import br.com.matraca.projetotcc.model.entity.Category;
import br.com.matraca.projetotcc.service.BoardService;
import br.com.matraca.projetotcc.service.ButtonService;
import br.com.matraca.projetotcc.service.CategoryService;
import br.com.matraca.projetotcc.service.Scraping;


@RestController
@RequestMapping("/")
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
        Iterable<Board> boards = boardService.getAll();
        Iterable<Category> categories = cservice.getAll();

        for(Button button : buttons){
            System.out.println(button);
        }
        // service.scrapImage();
        ModelAndView mv = new ModelAndView("index").addObject("buttons", buttons);
        mv.addObject("boards", boards);
        mv.addObject("categories", categories);

        return mv;
    }

    @PostMapping("update-layout")
    public ResponseEntity<String> updateButtonOrder(@RequestBody List<ButtonDTO> buttonsDTO) {
        service.saveLayoutButtons(buttonsDTO);
        for(ButtonDTO button: buttonsDTO){
            System.out.println(button);
        }

        return ResponseEntity.ok("Ordem dos botões salva no banco de dados!");
    }

    @PostMapping(value = "scrap", consumes = "text/plain", produces = "text/plain")
    public ResponseEntity<String> scrap(@RequestBody String buttonName) throws IOException {
        String url = scrap.getImage(buttonName);
        
        return ResponseEntity.ok(url);
    }

    @PatchMapping("{id}")
    public ResponseEntity<String> patchResource(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        service.patch(id, updates);
        return ResponseEntity.ok("Recurso atualizado parcialmente com sucesso!");
    }

    @PatchMapping("/")
    public ResponseEntity<String> patchResource(@RequestBody Map<String, Object> updates) throws IOException {

        String name = updates.containsKey("name") ? (String) updates.get("name") : "";
        String image = updates.containsKey("image") ? (String) updates.get("image") : "";
        String category = updates.containsKey("category") ? (String) updates.get("category") : null;
        String sound = updates.containsKey("sound") ? (String) updates.get("sound") : "";


        service.save(name, image, sound, category);
        return ResponseEntity.ok("Recurso atualizado parcialmente com sucesso!");
    }

    @PatchMapping("/list")
    public ResponseEntity<String> patchResource(@RequestBody List<Map<String, Object>> updatesList) throws IOException {

        for (Map<String, Object> updates : updatesList) {
            String name = updates.containsKey("name") ? (String) updates.get("name") : "";
            String image = updates.containsKey("image") ? (String) updates.get("image") : "";
            String category = updates.containsKey("category") ? (String) updates.get("category") : null;
            String sound = updates.containsKey("sound") ? (String) updates.get("sound") : "";

            service.save(name, image, sound, category);
        }

        return ResponseEntity.ok("Todos os recursos foram atualizados com sucesso!");
    }


    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteButton(@PathVariable Long id) {
        try {
            service.deleteButton(id);
            return ResponseEntity.ok("Botão excluído com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: Botão não encontrado.");
        }
    }

    @GetMapping("cadastrar")
    public void cadastrarButton() throws IOException {

        // Verbos
        service.save("comer", "", "som", "verbos");
        service.save("dormir", "", "som", "verbos");
        service.save("cagar", "", "som", "verbos");
        service.save("trabalhar", "", "som", "verbos");

        // Frutas
        service.save("maçã", "", "som", "frutas");
        service.save("banana", "", "som", "frutas");
        service.save("laranja", "", "som", "frutas");

        // Animais
        service.save("cachorro", "", "som", "animais");
        service.save("gato", "", "som", "animais");
        service.save("elefante", "", "som", "animais");

        // Sentimentos
        service.save("feliz", "", "som", "sentimentos");
        service.save("triste", "", "som", "sentimentos");
        service.save("bravo", "", "som", "sentimentos");

        // Cores
        service.save("vermelho", "", "som", "cores");
        service.save("azul", "", "som", "cores");
        service.save("verde", "", "som", "cores");
        service.save("amarelo", "", "som", "cores");

        // Locais
        service.save("casa", "", "som", "locais");
        service.save("escola", "", "som", "locais");
        service.save("parque", "", "som", "locais");
        service.save("supermercado", "", "som", "locais");

        // Profissões
        service.save("médico", "", "som", "profissões");
        service.save("professor", "", "som", "profissões");
        service.save("bombeiro", "", "som", "profissões");
        service.save("engenheiro", "", "som", "profissões");

        // Pronomes
        service.save("eu", "", "som", "pronomes");
        service.save("você", "", "som", "pronomes");
        service.save("ele", "", "som", "pronomes");
        service.save("nós", "", "som", "pronomes");

        // Números
        service.save("um", "", "som", "números");
        service.save("dois", "", "som", "números");
        service.save("três", "", "som", "números");
        service.save("quatro", "", "som", "números");
    }
}