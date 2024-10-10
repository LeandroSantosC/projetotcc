package br.com.matraca.projetotcc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.matraca.projetotcc.model.dto.ButtonRequestDTO;
import br.com.matraca.projetotcc.model.dto.ButtonResponseDTO;
import br.com.matraca.projetotcc.model.entity.Button;
import br.com.matraca.projetotcc.repository.ButtonRepository;

@RestController
@RequestMapping("button")
public class ButtonController {

    @Autowired //essa anotação serve para indicar para o spring que quando ele for instaciar o controller ele deve injetar essa dependencia 
    private ButtonRepository repository; // aqui eu indico o que o spring deve injetar quando for instaciar o controller, que será o repository do button
    
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public List<ButtonResponseDTO> getAll(){ //mudei de List de Button para ButtonResponseDTO como uma boa prática

        //List<Button> buttonList = repository.findAll(); //acesso ao repository para ter acesso aos dados dos botoes
        List<ButtonResponseDTO> buttonList = repository.findAll().stream().map(ButtonResponseDTO::new).toList(); //troquei para pegar os dados da lista, criar um funil e ainda mapear com o DTO
        return buttonList;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*") //é usado para evitar erro de CROSS pelo navegador PESQUISAR SOBRE
    @PostMapping
    public void saveButton(@RequestBody/*essa anotação é para usar o body da requisicao do servidor */ ButtonRequestDTO data){
        Button buttonData = new Button(data); //aqui eu converto o Request em tipo Button, pq o .save do spring só aceita parametros em forma do objeto
        repository.save(buttonData);
        return;
    }
}
