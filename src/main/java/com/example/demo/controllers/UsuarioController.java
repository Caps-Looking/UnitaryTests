package com.example.demo.controllers;

import com.example.demo.Exceptions.CustomException;
import com.example.demo.entitys.Usuario;
import com.example.demo.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<?> findAll() throws CustomException{
        return new ResponseEntity<>(usuarioService.findAll(), HttpStatus.OK);

    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> findOne(@PathVariable long id) throws CustomException {
        return new ResponseEntity<>(usuarioService.findOne(id), HttpStatus.OK);
    }

    @PostMapping(produces= "application/json")
    public ResponseEntity<?> save(@RequestBody Usuario usuario) throws CustomException{
        usuarioService.save(usuario);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) throws CustomException{
        usuarioService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Usuario usuario) throws CustomException{
        usuarioService.save(usuario);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }
}
