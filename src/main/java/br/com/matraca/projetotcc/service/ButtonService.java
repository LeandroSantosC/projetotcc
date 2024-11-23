package br.com.matraca.projetotcc.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.matraca.projetotcc.dto.ButtonDTO;
import br.com.matraca.projetotcc.model.entity.Button;
import br.com.matraca.projetotcc.model.entity.Category;
import br.com.matraca.projetotcc.repository.ButtonRepository;
import br.com.matraca.projetotcc.repository.CategoryRepository;
import jakarta.transaction.Transactional;

@Service
public class ButtonService {

    @Autowired
    ButtonRepository repository;

    @Autowired
    CategoryRepository crepository;

    @Autowired
    Scraping scrap;

    public static int orderCont;

    public Iterable<Button> getAll(){
        Iterable<Button> buttonList = repository.findAll();
        orderCont = (int) StreamSupport.stream(buttonList.spliterator(), false).count() + 1;
        return buttonList;
    }

    // @Async
    // public void scrapImage() throws IOException{
    //     Iterable<Button> buttonList = repository.findAll();
    //     for(Button button : buttonList){
    //         if("".equals(button.getImage())){
    //             button.setImage(scrap.getImage(button.getName()));
    //             repository.save(button);
    //         }
    //     }
    // }

    @Transactional // PRECISO APRENDER A USAR MELHOR, E ABSTRAIR MAIS ESSE MÉTODO SAVE
    public Button save(String name, String image, String sound, String category) throws IOException{     
        Button button = repository.findByName(name).orElseGet(() -> {
            return new Button();
        });

        Category categoryObject = crepository.findByName(category).orElseGet(() -> {
            Category object = new Category();
            object.setName(category);
            object.setButtons(new ArrayList<>());
            return object;
        });

        if(!categoryObject.getButtons().contains(button)){
            categoryObject.getButtons().add(button);
        }

        if("".equals(image)){
            image = scrap.getImage(name);
        }

        button.setImage(image);
        button.setName(name);
        button.setSound(sound);
        button.setCategory(categoryObject);
        button.setVisible(true);
        button.setPosition(orderCont++);

        return repository.save(button);
    }

    public void saveLayoutButtons(List<ButtonDTO> buttons){

        List<Button> buttonsAtt = new ArrayList<>();

        for(ButtonDTO button : buttons){
            Button buttonAtualizado = repository.findById(button.getId()).orElseThrow(() -> new RuntimeException(button.getId() + " ID de Botão não encontrado!"));

            buttonAtualizado.setPosition(button.getPosition());
            buttonAtualizado.setVisible(button.isVisible());

            buttonsAtt.add(buttonAtualizado);
        }

        repository.saveAll(buttonsAtt);
    }

    public void deleteButton(Long id) {
        Button button = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Botão não encontrado com ID: " + id));
        repository.delete(button);
    }

    public Iterable<Button> getAllByCategory(Category category){
        return this.repository.findAllByCategory(category);
    }

    public Optional<Button> getByName(String name){
        return this.repository.findByName(name);
    }

    public Optional<Button> getById(Long id){
        return this.repository.findById(id);
    }

    public void delete(Button button){
        repository.delete(button);
    }

}
