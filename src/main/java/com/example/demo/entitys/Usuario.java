package com.example.demo.entitys;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Usuario {

    int id;
    String nomeCompleto; //100
    String email; //50
    String tipodePessoa; //enum
    String cpf; //9
    String cnpj; //14
    String senha; //50 usar bycript posteriormente
    String tipoDeUsuario; //enum
}
