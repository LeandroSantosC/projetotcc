// package br.com.matraca.projetotcc.controller;

// import java.io.IOException;
// import java.util.List;
// import java.util.Map;
// import java.util.UUID;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PatchMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.servlet.ModelAndView;

// import br.com.matraca.projetotcc.dto.ButtonDTO;
// import br.com.matraca.projetotcc.model.entity.Board;
// import br.com.matraca.projetotcc.model.entity.Button;
// import br.com.matraca.projetotcc.model.entity.Category;
// import br.com.matraca.projetotcc.service.BoardService;
// import br.com.matraca.projetotcc.service.ButtonService;
// import br.com.matraca.projetotcc.service.CategoryService;
// import br.com.matraca.projetotcc.service.Scraping;


// @RestController
// @RequestMapping("/")
// public class ButtonController {

//     @Autowired //essa anotação serve para indicar para o spring que quando ele for instaciar o controller ele deve injetar essa dependencia 
//     private ButtonService service;

//     @Autowired
//     private BoardService boardService;

//     @Autowired
//     private CategoryService cservice;

//     @Autowired
//     private Scraping scrap;
    
//     @CrossOrigin(origins = "*", allowedHeaders = "*")
//     @GetMapping
//     public ModelAndView getPage() throws IOException{
//         Iterable<Button> buttons = service.getAll();
//         Iterable<Board> boards = boardService.getAll();
//         Iterable<Category> categories = cservice.getAll();

//         for(Button button : buttons){
//             System.out.println(button);
//         }
//         // service.scrapImage();
//         ModelAndView mv = new ModelAndView("index").addObject("buttons", buttons);
//         mv.addObject("boards", boards);
//         mv.addObject("categories", categories);

//         return mv;
//     }

//     @PostMapping("update-layout")
//     public ResponseEntity<String> updateButtonOrder(@RequestBody List<ButtonDTO> buttonsDTO) {
//         service.saveLayoutButtons(buttonsDTO);
//         for(ButtonDTO button: buttonsDTO){
//             System.out.println(button);
//         }

//         return ResponseEntity.ok("Ordem dos botões salva no banco de dados!");
//     }

//     @PostMapping(value = "scrap", consumes = "text/plain", produces = "text/plain")
//     public ResponseEntity<String> scrap(@RequestBody String buttonName) throws IOException {
//         String url = scrap.getImage(buttonName);
        
//         return ResponseEntity.ok(url);
//     }

//     @PatchMapping("{id}")
//     public ResponseEntity<String> patchResource(@PathVariable UUID id, @RequestBody Button updates) {
//         service.update(id, updates);
//         return ResponseEntity.ok("Recurso atualizado parcialmente com sucesso!");
//     }

//     @PatchMapping("/")
//     public ResponseEntity<String> patchResource(@RequestBody Map<String, Object> updates) throws IOException {

//         String name = updates.containsKey("name") ? (String) updates.get("name") : "";
//         String image = updates.containsKey("image") ? (String) updates.get("image") : "";
//         String category = updates.containsKey("category") ? (String) updates.get("category") : null;
//         String sound = updates.containsKey("sound") ? (String) updates.get("sound") : "";


//         service.create(name, image, sound, category);
//         return ResponseEntity.ok("Recurso atualizado parcialmente com sucesso!");
//     }

//     @PatchMapping("/list")
//     public ResponseEntity<String> patchResource(@RequestBody List<Map<String, Object>> updatesList) throws IOException {

//         for (Map<String, Object> updates : updatesList) {
//             String name = updates.containsKey("name") ? (String) updates.get("name") : "";
//             String image = updates.containsKey("image") ? (String) updates.get("image") : scrap.getImage(name);
//             String category = updates.containsKey("category") ? (String) updates.get("category") : null;
//             String sound = updates.containsKey("sound") ? (String) updates.get("sound") : "";

//             service.create(name, image, sound, category);
//         }

//         return ResponseEntity.ok("Todos os recursos foram atualizados com sucesso!");
//     }


//     @DeleteMapping("{id}")
//     public ResponseEntity<String> deleteButton(@PathVariable UUID id) {
//         try {
//             service.delete(id);
//             return ResponseEntity.ok("Botão excluído com sucesso.");
//         } catch (Exception e) {
//             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: Botão não encontrado.");
//         }
//     }

//     @GetMapping("cadastrar")
//     public void cadastrarButton() throws IOException {

//         // Verbos
//         service.create("comer", "", "som", "verbos");
//         service.create("dormir", "", "som", "verbos");
//         service.create("cagar", "", "som", "verbos");
//         service.create("trabalhar", "", "som", "verbos");

//         // Frutas
//         service.create("maçã", "", "som", "frutas");
//         service.create("banana", "", "som", "frutas");
//         service.create("laranja", "", "som", "frutas");

//         // Animais
//         service.create("cachorro", "", "som", "animais");
//         service.create("gato", "", "som", "animais");
//         service.create("elefante", "", "som", "animais");

//         // Sentimentos
//         service.create("feliz", "", "som", "sentimentos");
//         service.create("triste", "", "som", "sentimentos");
//         service.create("bravo", "", "som", "sentimentos");

//         // Cores
//         service.create("vermelho", "", "som", "cores");
//         service.create("azul", "", "som", "cores");
//         service.create("verde", "", "som", "cores");
//         service.create("amarelo", "", "som", "cores");

//         // Locais
//         service.create("casa", "", "som", "locais");
//         service.create("escola", "", "som", "locais");
//         service.create("parque", "", "som", "locais");
//         service.create("supermercado", "", "som", "locais");

//         // Profissões
//         service.create("médico", "", "som", "profissões");
//         service.create("professor", "", "som", "profissões");
//         service.create("bombeiro", "", "som", "profissões");
//         service.create("engenheiro", "", "som", "profissões");

//         // Pronomes
//         service.create("eu", "", "som", "pronomes");
//         service.create("você", "", "som", "pronomes");
//         service.create("ele", "", "som", "pronomes");
//         service.create("nós", "", "som", "pronomes");

//         // Números
//         service.create("um", "", "som", "números");
//         service.create("dois", "", "som", "números");
//         service.create("três", "", "som", "números");
//         service.create("quatro", "", "som", "números");
//     }
// }