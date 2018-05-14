package com.example.demo.controllers;

import com.example.demo.DemoApplication;
import com.example.demo.entitys.Usuario;
import com.example.demo.enums.TipoDePessoaEnum;
import com.example.demo.enums.TipoDeUsuarioEnum;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.GenericException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repositorys.UsuarioRepository;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasToString;
import static org.mockito.Mockito.*;



import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@WebAppConfiguration
@ContextConfiguration
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
        usuarioInvalido.setNomeCompleto(RandomStringUtils.randomAlphabetic(110));
        usuarioInvalido.setCpf(RandomStringUtils.randomAlphabetic(70));
        usuarioInvalido.setEmail(RandomStringUtils.randomAlphabetic(20));
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
        doThrow(BadRequestException.class).when(usuarioRepository).save(usuarioInvalido);
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
        when(usuarioRepository.getOne(usuarioValido.getId())).thenReturn(usuarioValido);
        io.restassured.module.mockmvc.RestAssuredMockMvc.get("/usuario/{id}", usuarioValido.getId())
                .then()
                .statusCode(200);
    }

    @Test
    public void buscarItemInexistentePeloID() {
        when(usuarioRepository.getOne(usuarioValido.getId())).thenThrow(NotFoundException.class);
        io.restassured.module.mockmvc.RestAssuredMockMvc.get("/usuario/{id}", usuarioValido.getId())
                .then()
                .statusCode(404);
    }

    @Test
    public void alterarItemInexistente() {
        when(usuarioRepository.getOne(usuarioValido.getId())).thenThrow(GenericException.class);
        io.restassured.module.mockmvc.RestAssuredMockMvc.get("/usuario/alterar/{id}", usuarioValido.getId())
                .then()
                .statusCode(404);
    }

    @Test
    public void excluirItemComDependencia() {
        when(usuarioRepository.getOne(usuarioValido.getId())).thenReturn(usuarioValido);
        doThrow(GenericException.class).when(usuarioRepository).deleteById(usuarioValido.getId());
        io.restassured.module.mockmvc.RestAssuredMockMvc.delete("/usuario/{id}", usuarioValido.getId())
                .then()
                .statusCode(412);
    }

    @Test
    public void excluirItemCadastrado() {
        when(usuarioRepository.getOne(usuarioValido.getId())).thenReturn(usuarioValido);
        doNothing().when(usuarioRepository).deleteById(usuarioValido.getId());
        io.restassured.module.mockmvc.RestAssuredMockMvc.delete("/usuario/{id}", usuarioValido.getId())
                .then()
                .statusCode(200);
    }

    @Test
    public void excluirItemInexistente() {
        when(usuarioRepository.getOne(usuarioValido.getId())).thenThrow(NotFoundException.class);
        doNothing().when(usuarioRepository).delete(usuarioValido);
        io.restassured.module.mockmvc.RestAssuredMockMvc.delete("/usuario/{id}", usuarioValido.getId())
                .then()
                .statusCode(404);
    }


}