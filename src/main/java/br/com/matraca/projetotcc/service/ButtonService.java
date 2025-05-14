package br.com.matraca.projetotcc.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import br.com.matraca.projetotcc.dto.ButtonDTO;
import br.com.matraca.projetotcc.model.entity.Button;
import br.com.matraca.projetotcc.model.entity.User;
import br.com.matraca.projetotcc.repository.ButtonRepository;
import jakarta.transaction.Transactional;

@Service
public class ButtonService {

    @Autowired
    ButtonRepository repository;

    @Autowired
    Scraping scrap;

    public List<Button> getAll() {
        List<Button> buttonList = repository.findAll();
        return buttonList;
    }

    public List<Button> findAllByUser(User user) {
        List<Button> buttonList = this.repository.findAllByUser(user);
        return buttonList;
    }

    @Async
    public void scrapImage() throws IOException {
        Iterable<Button> buttonList = repository.findAll();
        for (Button button : buttonList) {
            if ("".equals(button.getImage())) {
                button.setImage(scrap.getImage(button.getName()));
                repository.save(button);
            }
        }
    }

    // @Transactional // PRECISO APRENDER A USAR MELHOR, E ABSTRAIR MAIS ESSE MÉTODO SAVE
    // public Button create(String name, String image, String sound, Category category) throws IOException{     
    //     Button button = repository.findByName(name).orElseGet(() -> {
    //         Button newButton = new Button();
    //         newButton.setVisible(true);
    //         newButton.setPosition(orderCont++);
    //         return newButton;
    //     });
    //     category = categoryService.save(category);
    //     category.addButton(button);
    //     button.setCategory(category);
    //     if("".equals(image)){
    //         image = scrap.getImage(name);
    //     }
    //     button.setImage(image);
    //     button.setName(name);
    //     button.setSound(sound);
    //     return repository.save(button);
    // }
    @Transactional
    public Button create(User user, Button newButton) throws IOException {
        List<Button> userButtons = user.getButton();

        // Valida nome duplicado
        if (userButtons.stream().anyMatch(button -> button.getName().equalsIgnoreCase(newButton.getName()))) {
            throw new RuntimeException("Botão já existe, com o nome: " + newButton.getName());
        }

        newButton.setUser(user);
        newButton.setPosition(userButtons.size());

        if (newButton.getImage() == null || newButton.getImage().isEmpty()) {
            try {
                newButton.setImage(scrap.getImage(newButton.getName()));
            } catch (IOException e) {
                System.err.println("Erro ao obter imagem: " + e.getMessage());
                newButton.setImage("");
            }
        }

        userButtons.add(newButton);
        return repository.save(newButton);
    }

    @Transactional
    public void saveLayoutButtons(List<ButtonDTO> buttons) {

        List<Button> buttonsAtt = new ArrayList<>();

        for (ButtonDTO button : buttons) {
            Button buttonAtualizado = repository.findById(button.getId()).orElseThrow(() -> new RuntimeException(button.getId() + " ID de Botão não encontrado!"));

            buttonAtualizado.setPosition(button.getPosition());
            buttonAtualizado.setVisible(button.isVisible());

            buttonsAtt.add(buttonAtualizado);
        }

        repository.saveAll(buttonsAtt);
    }

    @Transactional
    public boolean delete(User user, Button button) throws RuntimeException {
        List<Button> userButtons = user.getButton();
        Button buttonToDelete = userButtons.stream()
                .filter(b -> b.getId().equals(button.getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Botão não encontrado: ID-" + button.getId()));

        return userButtons.remove(buttonToDelete);
    }

    @Transactional
    public Button update(User user, Button updates) {
        List<Button> userButtons = user.getButton();

        Button button = userButtons.stream()
                .filter(b -> b.getId().equals(updates.getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Botão não encontrado: ID-" + updates.getId()));

        if (updates.getName() != null) {
            button.setName(updates.getName());
        }
        if (updates.getImage() != null) {
            button.setImage(updates.getImage());
        }
        if (updates.getSound() != null) {
            button.setSound(updates.getSound());
        }
        if (updates.getCategory() != null && !updates.getCategory().equalsIgnoreCase(button.getCategory())) {
            button.setCategory(updates.getCategory());
        }

        return repository.save(button);
    }

    // @Transactional
    // public void delete(UUID id) {
    //     User user = userService.getByLogin(login);
    //     Button button = repository.findByIdAndUser(id, user)
    //         .orElseThrow(() -> new RuntimeException("Botão não encontrado com ID: " + id));
    //     repository.delete(button);
    // }
    public Iterable<Button> findAllByCategory(String category) {
        return this.repository.findAllByCategory(category);
    }

    public Optional<Button> findByName(String name) {
        return this.repository.findByName(name);
    }

    public Optional<Button> findById(UUID id) {
        return this.repository.findById(id);
    }

}
