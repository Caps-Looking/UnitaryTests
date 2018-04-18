package com.example.demo.enums;


import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TipoDeUsuarioEnum {

    ADMIN("Administrador do sistema"),
    EMPRESA("Empresa"),
    COMUM("Usuário comum");

    public int id;
    public String nome;

    TipoDeUsuarioEnum(String nome) {
        this.nome = nome;
        this.id = this.ordinal();
    }

}

