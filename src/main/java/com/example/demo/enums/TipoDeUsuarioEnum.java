package com.example.demo.enums;

public enum TipoDeUsuarioEnum {

    ADMIN("Administrador do sistema"),
    EMPRESA("Empresa"),
    COMUM("Usuário comum");


    public String nome;

    TipoDeUsuarioEnum(String nome) {
        this.nome = nome;
    }

}

