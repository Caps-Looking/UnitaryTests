package com.example.demo.entitys;

import com.example.demo.enums.TipoDePessoaEnum;
import com.example.demo.enums.TipoDeUsuarioEnum;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Usuario {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="usuario_generator")
    @SequenceGenerator(name = "usuario_generator",sequenceName = "usuario_generator",allocationSize = 1)
    long id;

    @NotNull
    @Size(max = 100)
    String nomeCompleto;

    @Size(max = 50)
    String email;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "tipo_pessoa")
    TipoDePessoaEnum tipodePessoaEnum;

    @Size(max = 11)
    String cpf; //9

    @Size(max = 14)
    String cnpj;

    @Size(max = 50)
    String senha;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "tipo_usuario")
    TipoDeUsuarioEnum tipoDeUsuarioEnum;

}
