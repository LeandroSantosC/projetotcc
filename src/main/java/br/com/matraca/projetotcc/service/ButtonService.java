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
    CategoryService categoryService;

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
    public Button create(String name, String image, String sound, String category) throws IOException{     
        Button button = repository.findByName(name).orElseGet(() -> {
            Button newButton = new Button();
            newButton.setVisible(true);
            newButton.setPosition(orderCont++);
            return newButton;
        });

        Category categoryObject = categoryService.save(category);
        button.setCategory(categoryObject);

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

    @Transactional // PRECISO APRENDER A USAR MELHOR, E ABSTRAIR MAIS ESSE MÉTODO SAVE
    public Button create(String name, String image, String sound, Category category) throws IOException{     
        Button button = repository.findByName(name).orElseGet(() -> {
            Button newButton = new Button();
            newButton.setVisible(true);
            newButton.setPosition(orderCont++);
            return newButton;
        });

        category = categoryService.save(category);
        category.addButton(button);
        button.setCategory(category);

        if("".equals(image)){
            image = scrap.getImage(name);
        }

        button.setImage(image);
        button.setName(name);
        button.setSound(sound);

        return repository.save(button);
    }

    @Transactional // PRECISO APRENDER A USAR MELHOR, E ABSTRAIR MAIS ESSE MÉTODO SAVE
    public Button create(Button newButton) throws IOException{ 
        
        if(repository.existsByName(newButton.getName())){
            throw new RuntimeException("Botão já existe com o nome: " + newButton.getName());
        }
        
        Category category = categoryService.save(newButton.getCategory());
        category.addButton(newButton);
        newButton.setCategory(category);
        newButton.setVisible(true);
        newButton.setPosition(orderCont++);

        if("".equals(newButton.getImage())){
            newButton.setImage(scrap.getImage(newButton.getName()));
        }

        return repository.save(newButton);
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

    public void update(Long id, Button updates){
        Button button = repository.findById(id)
        .orElseGet(() -> {
            Button newButton = new Button();
            newButton.setVisible(true);
            newButton.setPosition(orderCont++);
            return newButton;
        });

        if (updates.getName() != null) {
            button.setName(updates.getName());
        }
        if (updates.getImage() != null) {
            button.setImage(updates.getImage());
        }
        if (updates.getSound() != null) {
            button.setSound(updates.getSound());
        }
        if (updates.getCategory() != null) {
            Category category = categoryService.save(updates.getCategory());

            category.addButton(button);
            button.setCategory(category);
        }
    
        repository.save(button);
    }

    public void delete(Long id) {
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

}
