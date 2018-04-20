package com.example.demo.controllers;

import com.example.demo.Exceptions.CustomException;
import com.example.demo.entitys.Usuario;
import com.example.demo.enums.TipoDePessoaEnum;
import com.example.demo.enums.TipoDeUsuarioEnum;
import com.example.demo.repositorys.UsuarioRepository;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasToString;
import static org.mockito.Mockito.*;



import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    @MockBean
    UsuarioRepository usuarioRepository;


    private Usuario usuarioValido, usuarioInvalido;
    private MockMvc mockMvc;
    private List<Usuario> usuarioList;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        RestAssuredMockMvc.mockMvc(mockMvc);//falta dependencia


        this.usuarioList = new ArrayList<>();

        usuarioValido = new Usuario();
        usuarioValido.setNomeCompleto("Usuario válido");
        usuarioValido.setCpf("04854744170");
        usuarioValido.setEmail("email@email.com");
        usuarioValido.setTipodePessoaEnum(TipoDePessoaEnum.FISICA);
        usuarioValido.setTipoDeUsuarioEnum(TipoDeUsuarioEnum.COMUM);


        this.usuarioList.add(usuarioValido);

        usuarioInvalido = new Usuario();
        usuarioInvalido.setNomeCompleto(RandomStringUtils.randomAlphabetic(101));
        usuarioInvalido.setCpf(RandomStringUtils.randomAlphabetic(51));
        usuarioInvalido.setEmail(RandomStringUtils.randomAlphabetic(12));
        usuarioInvalido.setTipodePessoaEnum(TipoDePessoaEnum.FISICA);
        usuarioInvalido.setTipoDeUsuarioEnum(TipoDeUsuarioEnum.COMUM);

        this.usuarioList.add(usuarioInvalido);


    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void listarComListaVazia() throws Exception {
        List<Usuario> list = new ArrayList<>();
        when(usuarioRepository.findAll()).thenReturn(list);
        io.restassured.module.mockmvc.RestAssuredMockMvc.get("/usuario")
                .then()
                .statusCode(200)
                .body(String.valueOf("content".toString()), hasToString("[]"));
    }

    @Test
    public void listarComListaCheia() {
        when(usuarioRepository.findAll()).thenReturn(this.usuarioList);
        List<Usuario> response = io.restassured.module.mockmvc.RestAssuredMockMvc
                .when()
                .get("/usuario")
                .then()
                .statusCode(200)
                .body("content.cpf", hasItems("04854744170"))
                .extract()
                .path("content");
    }




    @Test
    public void cadastrarItemComDadosValidos() {
        when(usuarioRepository.save(usuarioValido)).thenReturn(usuarioValido);
        io.restassured.module.mockmvc.RestAssuredMockMvc.given()
                .contentType("application/json")
                .body(usuarioValido)
                .when()
                .post("/usuario")
                .then()
                .statusCode(200);
    }

    @Test
    public void cadastrarItemComDadosInvalidos() {
        doThrow(CustomException.class).when(usuarioRepository).save(usuarioValido);
        io.restassured.module.mockmvc.RestAssuredMockMvc.given()
                .contentType("application/json")
                .body(usuarioInvalido)
                .when()
                .post("/usuario")
                .then()
                .statusCode(400);
    }

//    @Test
//    public void cadastrarItemComDadosDuplicados() {
//        when(usuarioRepository.findUsuarioByCnpjOrAndCpf(usuarioValido.getCpf(), usuarioValido.getCnpj()).thenReturn(usuarioList),
//        io.restassured.module.mockmvc.RestAssuredMockMvc.given()
//                .contentType("application/json")
//                .body(usuarioValido)
//                .when()
//                .post("/usuario")
//                .then()
//                .statusCode(409);
//    }


    @Test
    public void buscarItemExistentePeloID() {
        when(usuarioRepository.findOne(usuarioValido.getId())).thenReturn(usuarioValido);
        io.restassured.module.mockmvc.RestAssuredMockMvc.get("/usuario/{id}", usuarioValido.getId())
                .then()
                .statusCode(200);
    }

    @Test
    public void buscarItemInexistentePeloID() {
        when(usuarioRepository.findOne(usuarioValido.getId())).thenThrow(CustomException.class);
        io.restassured.module.mockmvc.RestAssuredMockMvc.get("/usuario/{id}", usuarioValido.getId())
                .then()
                .statusCode(404);
    }

    @Test
    public void alterarItemInexistente() {
        when(usuarioRepository.findOne(usuarioValido.getId())).thenThrow(CustomException.class);
        io.restassured.module.mockmvc.RestAssuredMockMvc.get("/usuario/alterar/{id}", usuarioValido.getId())
                .then()
                .statusCode(404);
    }

    @Test
    public void excluirItemComDependencia() {
        when(usuarioRepository.findOne(usuarioValido.getId())).thenReturn(usuarioValido);
        doThrow(CustomException.class).when(usuarioRepository).delete(usuarioValido.getId());
        io.restassured.module.mockmvc.RestAssuredMockMvc.delete("/usuario/{id}", usuarioValido.getId())
                .then()
                .statusCode(412);
    }

    @Test
    public void excluirItemCadastrado() {
        when(usuarioRepository.findOne(usuarioValido.getId())).thenReturn(usuarioValido);
        doNothing().when(usuarioRepository).delete(usuarioValido.getId());
        io.restassured.module.mockmvc.RestAssuredMockMvc.delete("/usuario/{id}", usuarioValido.getId())
                .then()
                .statusCode(200);
    }

    @Test
    public void excluirItemInexistente() {
        when(usuarioRepository.findOne(usuarioValido.getId())).thenThrow(CustomException.class);
        doNothing().when(usuarioRepository).delete(usuarioValido);
        io.restassured.module.mockmvc.RestAssuredMockMvc.delete("/usuario/{id}", usuarioValido.getId())
                .then()
                .statusCode(404);
    }


}