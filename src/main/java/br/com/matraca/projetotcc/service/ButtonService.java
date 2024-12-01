package br.com.matraca.projetotcc.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
            Button newButton = new Button();
            newButton.setVisible(true);
            newButton.setPosition(orderCont++);
            return newButton;
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

    public void patch(Long id, Map<String, Object> updates){
        Button button = repository.findById(id)
        .orElseGet(() -> {
            Button newButton = new Button();
            newButton.setVisible(true);
            newButton.setPosition(orderCont++);
            return newButton;
        });

        if (updates.containsKey("name")) {
            button.setName((String) updates.get("name"));
        }
        if (updates.containsKey("image")) {
            button.setImage((String) updates.get("image"));
        }
        if (updates.containsKey("sound")) {
            button.setSound((String) updates.get("sound"));
        }
        if (updates.containsKey("category")) {
            Category category = crepository.findByName((String) updates.get("category"))
            .orElseGet(() -> {
                Category newCategory = new Category();
                newCategory.setName((String) updates.get("category"));
                newCategory.setButtons(new ArrayList<>());
                return newCategory;
            });

            if(!category.getButtons().contains(button)){
                category.getButtons().add(button);
            }

            button.setCategory(category);
        }
    
        repository.save(button);
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
