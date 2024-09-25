package br.com.matraca.projetotcc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.matraca.projetotcc.figura.Figura;

@RestController
@RequestMapping("figura")
public class FiguraController {

    @GetMapping
    public void getAll(){

        Figura figura

    }
}
