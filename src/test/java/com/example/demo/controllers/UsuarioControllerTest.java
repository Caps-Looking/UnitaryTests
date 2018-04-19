package com.example.demo.controllers;

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
    private List<Usuario> provinciaList;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        RestAssuredMockMvc.mockMvc(mockMvc);//falta dependencia


        this.provinciaList = new ArrayList<>();

        usuarioValido = new Usuario();
        usuarioValido.setNomeCompleto("Usuario válido");
        usuarioValido.setCpf("04854744170");
        usuarioValido.setEmail("email@email.com");
        usuarioValido.setTipodePessoaEnum(TipoDePessoaEnum.FISICA);
        usuarioValido.setTipoDeUsuarioEnum(TipoDeUsuarioEnum.COMUM);


        this.provinciaList.add(usuarioValido);

        usuarioInvalido = new Usuario();
        usuarioInvalido.setNomeCompleto(RandomStringUtils.randomAlphabetic(101));
        usuarioInvalido.setCpf(RandomStringUtils.randomAlphabetic(51));
        usuarioInvalido.setEmail(RandomStringUtils.randomAlphabetic(12));
        usuarioInvalido.setTipodePessoaEnum(TipoDePessoaEnum.FISICA);
        usuarioInvalido.setTipoDeUsuarioEnum(TipoDeUsuarioEnum.COMUM);

        this.provinciaList.add(usuarioInvalido);


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
        when(usuarioRepository.findAll()).thenReturn(this.provinciaList);
        List<Usuario> response = io.restassured.module.mockmvc.RestAssuredMockMvc
                .when()
                .get("/usuario")
                .then()
                .statusCode(200)
                .body("content.cpf", hasItems("04854744170"))
                .extract()
                .path("content");
    }

    //implementar outros teste, service e controller





}