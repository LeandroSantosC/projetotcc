package br.com.matraca.projetotcc.model.enums;

public enum Role {
    PUBLIC("PUBLIC"),
    USER("USER"),
    ADMIN("ADMIN");

    private String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
