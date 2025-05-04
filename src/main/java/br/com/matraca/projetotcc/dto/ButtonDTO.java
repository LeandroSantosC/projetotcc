package br.com.matraca.projetotcc.dto;

import java.util.UUID;

import br.com.matraca.projetotcc.model.entity.Category;

public class ButtonDTO {

    private UUID id;
    private String name;
    private String image;
    private String sound;
    private Category category;
    private int position;
    private boolean isVisible;

    public ButtonDTO(){
    }

    
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getImage() {
        return image;
    }


    public void setImage(String image) {
        this.image = image;
    }


    public String getSound() {
        return sound;
    }


    public void setSound(String sound) {
        this.sound = sound;
    }


    public Category getCategory() {
        return category;
    }


    public void setCategory(Category category) {
        this.category = category;
    }


    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }


    // Getters e Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setIsVisible(boolean visible) {
        this.isVisible = visible;
    }
}

