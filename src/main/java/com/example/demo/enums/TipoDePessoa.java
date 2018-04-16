package com.example.demo.enums;


import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)

public enum TipoDePessoa {
    FISICA("Pessoa física"),
    JURIDICA("Pessoa juridica");

    public int id;
    public String nome;

    TipoDePessoa(String nome) {
        this.nome = nome;
        this.id = this.ordinal();
    }
}
