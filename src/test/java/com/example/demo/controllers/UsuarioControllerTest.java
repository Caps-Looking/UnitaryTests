package com.example.demo.controllers;

import com.example.demo.entitys.Usuario;
import com.example.demo.repositorys.UsuarioRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


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



    }

    @After
    public void tearDown() throws Exception {
    }



}