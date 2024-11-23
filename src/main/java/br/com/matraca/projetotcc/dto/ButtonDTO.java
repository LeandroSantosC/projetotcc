package br.com.matraca.projetotcc.dto;

public class ButtonDTO {

    private Long id;
    private int position;
    private boolean isVisible;

    public ButtonDTO(){
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

