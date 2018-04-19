package com.example.demo.entitys;

import com.example.demo.enums.TipoDePessoaEnum;
import com.example.demo.enums.TipoDeUsuarioEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="usuario_generator")
    @SequenceGenerator(name = "usuario_generator",sequenceName = "usuario_generator",allocationSize = 1)
    int id;

    @Size(max = 100)
    @Column(name = "nome-completo")
    String nomeCompleto; //100

    @Size(max = 50)
    @Column(name = "email")
    String email; //50

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "tipo-de-pessoa")
    TipoDePessoaEnum tipodePessoaEnum; //enum

    @Size(max = 11)
    @Column(name = "cpf")
    String cpf; //9

    @Size(max = 14)
    @Column(name = "cnpj")
    String cnpj; //14

    @Size(max = 50)
    @Column(name = "senha")
    String senha; //50 usar bycript posteriormente

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "tipo-de-usuario")
    TipoDeUsuarioEnum tipoDeUsuarioEnum; //enum

}
