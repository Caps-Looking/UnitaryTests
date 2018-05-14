package com.example.demo.enums;


public enum TipoDePessoaEnum {
    FISICA("Pessoa física"),
    JURIDICA("Pessoa juridica");


    public String nome;

    TipoDePessoaEnum(String nome) {
        this.nome = nome;
    }
}
